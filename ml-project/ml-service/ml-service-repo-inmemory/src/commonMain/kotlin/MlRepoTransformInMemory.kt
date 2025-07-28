package api.kotlinproject.repo.inmemory

import api.kotlinproject.common.models.MdlMlId

import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.repo.*

import api.kotlinproject.repo.common.IRepoMlTransformInitializable
import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class MlRepoTransformInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : MlRepoTransformBase(), IRepoMlTransform, IRepoMlTransformInitializable {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, MlTransformEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(mls: Collection<MdlMlTransform>) = mls.map { ml ->
        val entity = MlTransformEntity(ml)
        require(entity.id != null)
        cache.put(entity.id, entity)
        ml
    }

    override suspend fun createMlTransform(rq: DbMlTransformRequest): IDbMlTransformResponse = tryMlTransformMethod {
        val key = randomUuid()
        val ml = rq.ml.copy(id = MdlMlId(key))
        val entity = MlTransformEntity(ml)
        mutex.withLock {
            cache.put(key, entity)
        }
        DbMlTransformResponseOk(ml)
    }

    override suspend fun readMlTransform(rq: DbMlIdTransformRequest): IDbMlTransformResponse = tryMlTransformMethod {
        val key = rq.id.takeIf { it != MdlMlId.NONE }?.asString() ?: return@tryMlTransformMethod errorTransformEmptyId
        mutex.withLock {
            cache.get(key)
                ?.let {
                    DbMlTransformResponseOk(it.toInternal())
                } ?: errorTransformNotFound(rq.id)
        }
    }

    override suspend fun updateMlTransform(rq: DbMlTransformRequest): IDbMlTransformResponse = tryMlTransformMethod {
        val rqMl = rq.ml
        val id = rqMl.id.takeIf { it != MdlMlId.NONE } ?: return@tryMlTransformMethod errorTransformEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldMl = cache.get(key)?.toInternal()
            when {
                oldMl == null -> errorTransformNotFound(id)
                else -> {
                    val newMl = rqMl.copy()
                    val entity = MlTransformEntity(newMl)
                    cache.put(key, entity)
                    DbMlTransformResponseOk(newMl)
                }
            }
        }
    }


    override suspend fun deleteMlTransform(rq: DbMlIdTransformRequest): IDbMlTransformResponse = tryMlTransformMethod {
        val id = rq.id.takeIf { it != MdlMlId.NONE } ?: return@tryMlTransformMethod errorTransformEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldMl = cache.get(key)?.toInternal()
            when {
                oldMl == null -> errorTransformNotFound(id)
                else -> {
                    cache.invalidate(key)
                    DbMlTransformResponseOk(oldMl)
                }
            }
        }
    }

}

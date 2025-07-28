package api.kotlinproject.repo.inmemory

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTrainResult
import api.kotlinproject.common.repo.*
import api.kotlinproject.repo.common.IRepoMlTrainResultInitializable
import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class MlRepoTrainResultInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : MlRepoTrainResultBase(), IRepoMlTrainResult, IRepoMlTrainResultInitializable {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, MlTrainResultEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(mls: Collection<MdlMlTrainResult>) = mls.map { ml ->
        val entity = MlTrainResultEntity(ml)
        require(entity.id != null)
        cache.put(entity.id, entity)
        ml
    }

    override suspend fun createMlTrainResult(rq: DbMlTrainResultRequest): IDbMlTrainResultResponse = tryMlTrainResultMethod {
        val key = randomUuid()
        val ml = rq.ml.copy(id = MdlMlId(key))
        val entity = MlTrainResultEntity(ml)
        mutex.withLock {
            cache.put(key, entity)
        }
        DbMlTrainResultResponseOk(ml)
    }

    override suspend fun readMlTrainResult(rq: DbMlIdTrainResultRequest): IDbMlTrainResultResponse = tryMlTrainResultMethod {
        val key = rq.id.takeIf { it != MdlMlId.NONE }?.asString() ?: return@tryMlTrainResultMethod errorTrainResultEmptyId
        mutex.withLock {
            cache.get(key)
                ?.let {
                    DbMlTrainResultResponseOk(it.toInternal())
                } ?: errorTrainResultNotFound(rq.id)
        }
    }

    override suspend fun updateMlTrainResult(rq: DbMlTrainResultRequest): IDbMlTrainResultResponse = tryMlTrainResultMethod {
        val rqMl = rq.ml
        val id = rqMl.id.takeIf { it != MdlMlId.NONE } ?: return@tryMlTrainResultMethod errorTrainResultEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldMl = cache.get(key)?.toInternal()
            when {
                oldMl == null -> errorTrainResultNotFound(id)
                else -> {
                    val newMl = rqMl.copy()
                    val entity = MlTrainResultEntity(newMl)
                    cache.put(key, entity)
                    DbMlTrainResultResponseOk(newMl)
                }
            }
        }
    }


    override suspend fun deleteMlTrainResult(rq: DbMlIdTrainResultRequest): IDbMlTrainResultResponse = tryMlTrainResultMethod {
        val id = rq.id.takeIf { it != MdlMlId.NONE } ?: return@tryMlTrainResultMethod errorTrainResultEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldMl = cache.get(key)?.toInternal()
            when {
                oldMl == null -> errorTrainResultNotFound(id)
                else -> {
                    cache.invalidate(key)
                    DbMlTrainResultResponseOk(oldMl)
                }
            }
        }
    }

}

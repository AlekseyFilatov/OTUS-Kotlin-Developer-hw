package api.kotlinproject.repo.inmemory

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.repo.*
import api.kotlinproject.repo.common.IRepoMlInitializable
import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class MlRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : MlRepoBase(), IRepoMl, IRepoMlInitializable {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, MlEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(mls: Collection<MdlMl>) = mls.map { ml ->
        val entity = MlEntity(ml)
        require(entity.id != null)
        cache.put(entity.id, entity)
        ml
    }

    override suspend fun createMl(rq: DbMlRequest): IDbMlResponse = tryMlMethod {
        //val key = randomUuid()
        val key = rq.ml.id.asString()
        val ml = rq.ml.copy(id = MdlMlId(key))
        val entity = MlEntity(ml)
        mutex.withLock {
            cache.put(key, entity)
        }
        DbMlResponseOk(ml)
    }

    override suspend fun readMl(rq: DbMlIdRequest): IDbMlResponse = tryMlMethod {
        val key = rq.id.takeIf { it != MdlMlId.NONE }?.asString() ?: return@tryMlMethod errorEmptyId
        mutex.withLock {
            cache.get(key)
                ?.let {
                    DbMlResponseOk(it.toInternal())
                } ?: errorNotFound(rq.id)
        }
    }

    override suspend fun updateMl(rq: DbMlRequest): IDbMlResponse = tryMlMethod {
        val rqMl = rq.ml
        val id = rqMl.id.takeIf { it != MdlMlId.NONE } ?: return@tryMlMethod errorEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldMl = cache.get(key)?.toInternal()
            when {
                oldMl == null -> errorNotFound(id)
                else -> {
                    val newMl = rqMl.copy()
                    val entity = MlEntity(newMl)
                    cache.put(key, entity)
                    DbMlResponseOk(newMl)
                }
            }
        }
    }


    override suspend fun deleteMl(rq: DbMlIdRequest): IDbMlResponse = tryMlMethod {
        val id = rq.id.takeIf { it != MdlMlId.NONE } ?: return@tryMlMethod errorEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldMl = cache.get(key)?.toInternal()
            when {
                oldMl == null -> errorNotFound(id)
                else -> {
                    cache.invalidate(key)
                    DbMlResponseOk(oldMl)
                }
            }
        }
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchMl(rq: DbMlFilterRequest): IDbMlsResponse = tryMlsMethod {
        val result: List<MdlMl> = cache.asMap().asSequence()
           /* .filter { entry ->
                rq.ownerId.takeIf { it != MdlUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }*/
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.title?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        DbMlsResponseOk(result)
    }
}

package api.kotlinproject.backend.repo.cassandra

import api.kotlinproject.backend.repo.cassandra.model.MlCassandraDTO
import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.repo.*
import api.kotlinproject.repo.common.IRepoMlInitializable
import com.benasher44.uuid.uuid4
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.AsyncResultSet
import com.datastax.oss.driver.internal.core.type.codec.registry.DefaultCodecRegistry
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import java.net.InetAddress
import java.net.InetSocketAddress
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class RepoMlCassandra(
    private val keyspaceName: String,
    private val host: String = "",
    private val port: Int = 9042,
    private val user: String = "cassandra",
    private val pass: String = "cassandra",
    private val dc: String = "dc1",
    private val timeout: Duration = 30.toDuration(DurationUnit.SECONDS),
    private val randomUuid: () -> String = { uuid4().toString() },
    initObjects: Collection<MdlMl> = emptyList(),
) : MlRepoBase(), IRepoMl, IRepoMlInitializable {
    private val codecRegistry by lazy {
        DefaultCodecRegistry("default").apply {

        }
    }

    private val session by lazy {
        CqlSession.builder()
            .addContactPoints(parseAddresses(host, port))
            .withLocalDatacenter(dc)
            .withAuthCredentials(user, pass)
            .withCodecRegistry(codecRegistry)
            .withKeyspace(keyspaceName)
            .build()
    }

    private val mapper by lazy { CassandraMapper.builder(session).build() }

    private val dao by lazy {
        mapper.adDao(keyspaceName, MlCassandraDTO.TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout) {
                        create(MlCassandraDTO(model)).await()
                    }
                }
            }
        }
    }

    fun clear() = dao.deleteAll()

    override fun save(mls: Collection<MdlMl>): Collection<MdlMl> = mls.onEach { dao.create(MlCassandraDTO(it)) }

    override suspend fun createMl(rq: DbMlRequest): IDbMlResponse = tryMlMethod {
        val new = rq.ml.copy(id = MdlMlId(randomUuid())/*, lock = MdlMlLock(randomUuid())*/)
        dao.create(MlCassandraDTO(new)).await()
        DbMlResponseOk(new)
    }

    override suspend fun readMl(rq: DbMlIdRequest): IDbMlResponse = tryMlMethod {
        if (rq.id == MdlMlId.NONE) return@tryMlMethod errorEmptyId
        val res = dao.reml(rq.id.asString()).await() ?: return@tryMlMethod errorNotFound(rq.id)
        DbMlResponseOk(res.toMlModel())
    }

    override suspend fun updateMl(rq: DbMlRequest): IDbMlResponse = tryMlMethod {
        val idStr = rq.ml.id.asString()
        //val prevLock = rq.ml.lock.asString()
        val new = rq.ml.copy(/*lock = MdlMlLock(randomUuid())*/)
        val dto = MlCassandraDTO(new)

        val res: AsyncResultSet = dao.update(dto/*, prevLock*/).await()
        val isSuccess = res.wasApplied()
        val resultField = res.one()
        when {
            // Два варианта почти эквивалентны, выбирайте который вам больше подходит
            isSuccess -> DbMlResponseOk(new)
            //res.wasApplied() -> DbMlResponse.success(dao.reml(idStr).await()?.toMlModel())
            resultField == null -> errorNotFound(rq.ml.id)
            else -> errorRepoConcurrency(
                oldMl = dao.reml(idStr).await()?.toMlModel() ?: throw Exception(
                    "Consistency DB problem: Object with ID $idStr " +
                            "was denied for update but the same object was not found in db at further request"
                ),
                //expectedLock = rq.ml.lock
            )
        }
    }

    override suspend fun deleteMl(rq: DbMlIdRequest): IDbMlResponse = tryMlMethod {
        val idStr = rq.id.asString()
        //val prevLock = rq.lock.asString()
        val oldMl = dao.reml(idStr).await()?.toMlModel() ?: return@tryMlMethod errorNotFound(rq.id)
        val res = dao.delete(idStr/*, prevLock*/).await()
        val isSuccess = res.wasApplied()
        val resultField = res.one()
        when {
            // Два варианта почти эквивалентны, выбирайте который вам больше подходит
            isSuccess -> DbMlResponseOk(oldMl)
            resultField == null -> errorNotFound(rq.id)
            else -> errorRepoConcurrency(
                dao.reml(idStr).await()?.toMlModel() ?: throw Exception(
                    "Consistency DB problem: Object with ID $idStr " +
                            "was successfully reml but was denied for delete"
                ),
                //rq.lock
            )
        }
    }

    override suspend fun searchMl(rq: DbMlFilterRequest): IDbMlsResponse = tryMlsMethod {
        val found = dao.search(rq).await()
        DbMlsResponseOk(found.map { it.toMlModel() })
    }

    private fun parseAddresses(hosts: String, port: Int): Collection<InetSocketAddress> = hosts
        .split(Regex("""\s*,\s*"""))
        .map { InetSocketAddress(InetAddress.getByName(it), port) }
}

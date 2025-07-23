package api.kotlinproject.backend.repo.cassandra


import api.kotlinproject.backend.repo.cassandra.model.MlCassandraTrainResultDTO
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTrainResult
import api.kotlinproject.common.repo.*
import api.kotlinproject.repo.common.IRepoMlTrainResultInitializable
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

class RepoMlTrainResultCassandra(
    private val keyspaceName: String,
    private val host: String = "",
    private val port: Int = 9042,
    private val user: String = "cassandra",
    private val pass: String = "cassandra",
    private val dc: String = "dc1",
    private val timeout: Duration = 30.toDuration(DurationUnit.SECONDS),
    private val randomUuid: () -> String = { uuid4().toString() },
    initObjects: Collection<MdlMlTrainResult> = emptyList(),
) : MlRepoTrainResultBase(), IRepoMlTrainResult, IRepoMlTrainResultInitializable {
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

    private val mapper by lazy { CassandraTrainResultMapper.builder(session).build() }

    private val dao by lazy {
        mapper.adDao(keyspaceName, MlCassandraTrainResultDTO.TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout) {
                        create(MlCassandraTrainResultDTO(model)).await()
                    }
                }
            }
        }
    }

    fun clear() = dao.deleteAll()

    override fun save(mls: Collection<MdlMlTrainResult>): Collection<MdlMlTrainResult> = mls.onEach { dao.create(MlCassandraTrainResultDTO(it)) }

    override suspend fun createMlTrainResult(rq: DbMlTrainResultRequest): IDbMlTrainResultResponse = tryMlTrainResultMethod {
        val new = rq.ml.copy(id = MdlMlId(randomUuid())/*, lock = MdlMlLock(randomUuid())*/)
        dao.create(MlCassandraTrainResultDTO(new)).await()
        DbMlTrainResultResponseOk(new)
    }

    override suspend fun readMlTrainResult(rq: DbMlIdTrainResultRequest): IDbMlTrainResultResponse = tryMlTrainResultMethod {
        if (rq.id == MdlMlId.NONE) return@tryMlTrainResultMethod errorTrainResultEmptyId
        val res = dao.reml(rq.id.asString()).await() ?: return@tryMlTrainResultMethod errorTrainResultNotFound(rq.id)
        DbMlTrainResultResponseOk(res.toMlTrainResultModel())
    }

    override suspend fun updateMlTrainResult(rq: DbMlTrainResultRequest): IDbMlTrainResultResponse = tryMlTrainResultMethod {
        val idStr = rq.ml.id.asString()
        //val prevLock = rq.ml.lock.asString()
        val new = rq.ml.copy(/*lock = MdlMlLock(randomUuid())*/)
        val dto = MlCassandraTrainResultDTO(new)

        val res: AsyncResultSet = dao.update(dto/*, prevLock*/).await()
        val isSuccess = res.wasApplied()
        val resultField = res.one()
        when {
            // Два варианта почти эквивалентны, выбирайте который вам больше подходит
            isSuccess -> DbMlTrainResultResponseOk(new)
            // res.wasApplied() -> DbMlResponse.success(dao.reml(idStr).await()?.toMlModel())
            resultField == null -> errorTrainResultNotFound(rq.ml.id)
            else -> errorTrainResultRepoConcurrency(
                oldMl = dao.reml(idStr).await()?.toMlTrainResultModel() ?: throw Exception(
                    "Consistency DB problem: Object with ID $idStr " +
                            "was denied for update but the same object was not found in db at further request"
                ),
                //expectedLock = rq.ml.lock
            )
        }
    }

    override suspend fun deleteMlTrainResult(rq: DbMlIdTrainResultRequest): IDbMlTrainResultResponse = tryMlTrainResultMethod {
        val idStr = rq.id.asString()
        //val prevLock = rq.lock.asString()
        val oldMl = dao.reml(idStr).await()?.toMlTrainResultModel() ?: return@tryMlTrainResultMethod errorTrainResultNotFound(rq.id)
        val res = dao.delete(idStr/*, prevLock*/).await()
        val isSuccess = res.wasApplied()
        val resultField = res.one()
        when {
            // Два варианта почти эквивалентны, выбирайте который вам больше подходит
            isSuccess -> DbMlTrainResultResponseOk(oldMl)
            resultField == null -> errorTrainResultNotFound(rq.id)
            else -> errorTrainResultRepoConcurrency(
                dao.reml(idStr).await()?.toMlTrainResultModel() ?: throw Exception(
                    "Consistency DB problem: Object with ID $idStr " +
                            "was successfully reml but was denied for delete"
                ),
                //rq.lock
            )
        }
    }

    /*override suspend fun searchMl(rq: DbMlFilterRequest): IDbMlsResponse = tryMlsMethod {
        val found = dao.search(rq).await()
        DbMlsResponseOk(found.map { it.toMlModel() })
    }*/

    private fun parseAddresses(hosts: String, port: Int): Collection<InetSocketAddress> = hosts
        .split(Regex("""\s*,\s*"""))
        .map { InetSocketAddress(InetAddress.getByName(it), port) }
}

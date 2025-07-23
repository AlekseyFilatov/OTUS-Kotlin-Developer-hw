package api.kotlinproject.backend.repo.cassandra


import api.kotlinproject.backend.repo.cassandra.model.MlCassandraTransformDTO
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.repo.*
import api.kotlinproject.repo.common.IRepoMlTransformInitializable
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

class RepoMlTransformCassandra(
    private val keyspaceName: String,
    private val host: String = "",
    private val port: Int = 9042,
    private val user: String = "cassandra",
    private val pass: String = "cassandra",
    private val dc: String = "dc1",
    private val timeout: Duration = 30.toDuration(DurationUnit.SECONDS),
    private val randomUuid: () -> String = { uuid4().toString() },
    initObjects: Collection<MdlMlTransform> = emptyList(),
) : MlRepoTransformBase(), IRepoMlTransform, IRepoMlTransformInitializable {
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

    private val mapper by lazy { CassandraTransformMapper.builder(session).build() }

    private val dao by lazy {
        mapper.adDao(keyspaceName, MlCassandraTransformDTO.TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout) {
                        create(MlCassandraTransformDTO(model)).await()
                    }
                }
            }
        }
    }

    fun clear() = dao.deleteAll()

    override fun save(mls: Collection<MdlMlTransform>): Collection<MdlMlTransform> = mls.onEach { dao.create(MlCassandraTransformDTO(it)) }

    override suspend fun createMlTransform(rq: DbMlTransformRequest): IDbMlTransformResponse = tryMlTransformMethod {
        val new = rq.ml.copy(id = MdlMlId(randomUuid())/*, lock = MdlMlLock(randomUuid())*/)
        dao.create(MlCassandraTransformDTO(new)).await()
        DbMlTransformResponseOk(new)
    }

    override suspend fun readMlTransform(rq: DbMlIdTransformRequest): IDbMlTransformResponse = tryMlTransformMethod {
        if (rq.id == MdlMlId.NONE) return@tryMlTransformMethod errorTransformEmptyId
        val res = dao.reml(rq.id.asString()).await() ?: return@tryMlTransformMethod errorTransformNotFound(rq.id)
        DbMlTransformResponseOk(res.toMlTransformModel())
    }

    override suspend fun updateMlTransform(rq: DbMlTransformRequest): IDbMlTransformResponse = tryMlTransformMethod {
        val idStr = rq.ml.id.asString()
        //val prevLock = rq.ml.lock.asString()
        val new = rq.ml.copy(/*lock = MdlMlLock(randomUuid())*/)
        val dto = MlCassandraTransformDTO(new)

        val res: AsyncResultSet = dao.update(dto/*, prevLock*/).await()
        val isSuccess = res.wasApplied()
        val resultField = res.one()
        when {
            // Два варианта почти эквивалентны, выбирайте который вам больше подходит
            isSuccess -> DbMlTransformResponseOk(new)
            // res.wasApplied() -> DbMlResponse.success(dao.reml(idStr).await()?.toMlModel())
            resultField == null -> errorTransformNotFound(rq.ml.id)
            else -> errorTransformRepoConcurrency(
                oldMl = dao.reml(idStr).await()?.toMlTransformModel() ?: throw Exception(
                    "Consistency DB problem: Object with ID $idStr " +
                            "was denied for update but the same object was not found in db at further request"
                ),
                //expectedLock = rq.ml.lock
            )
        }
    }

    override suspend fun deleteMlTransform(rq: DbMlIdTransformRequest): IDbMlTransformResponse = tryMlTransformMethod {
        val idStr = rq.id.asString()
        //val prevLock = rq.lock.asString()
        val oldMl = dao.reml(idStr).await()?.toMlTransformModel() ?: return@tryMlTransformMethod errorTransformNotFound(rq.id)
        val res = dao.delete(idStr/*, prevLock*/).await()
        val isSuccess = res.wasApplied()
        val resultField = res.one()
        when {
            // Два варианта почти эквивалентны, выбирайте который вам больше подходит
            isSuccess -> DbMlTransformResponseOk(oldMl)
            resultField == null -> errorTransformNotFound(rq.id)
            else -> errorTransformRepoConcurrency(
                dao.reml(idStr).await()?.toMlTransformModel() ?: throw Exception(
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

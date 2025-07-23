package api.kotlinproject.backend.repo.cassandra

import api.kotlinproject.backend.repo.cassandra.model.MlCassandraDTO
import api.kotlinproject.common.repo.DbMlFilterRequest
import com.datastax.oss.driver.api.core.cql.AsyncResultSet
import com.datastax.oss.driver.api.mapper.annotations.*
import java.util.concurrent.CompletionStage

@Dao
interface MlCassandraDAO {
    @Insert
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun create(dto: MlCassandraDTO): CompletionStage<MlCassandraDTO>

    @Select
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    fun reml(id: String): CompletionStage<MlCassandraDTO?>

    @Update(/*customIfClause = "${COLUMN_LOCK} = :prevLock"*/)
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun update(dto: MlCassandraDTO/*, prevLock: String*/): CompletionStage<AsyncResultSet>

    @Delete(customWhereClause = "id = :id", /*customIfClause = "${COLUMN_LOCK} = :prevLock",*/ entityClass = [MlCassandraDTO::class])
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun delete(id: String/*, prevLock: String*/): CompletionStage<AsyncResultSet>

    @Query("TRUNCATE ${MlCassandraDTO.TABLE_NAME}")
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun deleteAll()

    @QueryProvider(providerClass = MlCassandraSearchProvider::class, entityHelpers = [MlCassandraDTO::class])
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    fun search(filter: DbMlFilterRequest): CompletionStage<Collection<MlCassandraDTO>>
}

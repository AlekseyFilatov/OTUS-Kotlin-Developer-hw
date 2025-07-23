package api.kotlinproject.backend.repo.cassandra

import api.kotlinproject.backend.repo.cassandra.model.MlCassandraTrainResultDTO
import com.datastax.oss.driver.api.core.cql.AsyncResultSet
import com.datastax.oss.driver.api.mapper.annotations.*
import java.util.concurrent.CompletionStage

@Dao
interface MlCassandraTrainResultDAO {
    @Insert
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun create(dto: MlCassandraTrainResultDTO): CompletionStage<MlCassandraTrainResultDTO>

    @Select
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    fun reml(id: String): CompletionStage<MlCassandraTrainResultDTO?>

    @Update( /*customIfClause = "${COLUMN_LOCK} = :prevLock"*/)
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun update(dto: MlCassandraTrainResultDTO/*, prevLock: String*/): CompletionStage<AsyncResultSet>

    @Delete(customWhereClause = "id = :id", /*customIfClause = "${COLUMN_LOCK} = :prevLock",*/ entityClass = [MlCassandraTrainResultDTO::class])
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun delete(id: String/*, prevLock: String*/): CompletionStage<AsyncResultSet>

    @Query("TRUNCATE ${MlCassandraTrainResultDTO.TABLE_NAME}")
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun deleteAll()

    /*@QueryProvider(providerClass = MlCassandraSearchProvider::class, entityHelpers = [MlCassandraDTO::class])
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    fun search(filter: DbMlFilterRequest): CompletionStage<Collection<MlCassandraDTO>>*/
}

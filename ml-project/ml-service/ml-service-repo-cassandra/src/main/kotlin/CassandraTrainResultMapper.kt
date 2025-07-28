package api.kotlinproject.backend.repo.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace
import com.datastax.oss.driver.api.mapper.annotations.DaoTable
import com.datastax.oss.driver.api.mapper.annotations.Mapper

@Mapper
interface CassandraTrainResultMapper {
    @DaoFactory
    fun adDao(@DaoKeyspace keyspace: String, @DaoTable tableName: String): MlCassandraTrainResultDAO

    companion object {
        fun builder(session: CqlSession) = CassandraTrainResultMapperBuilder(session)
    }
}
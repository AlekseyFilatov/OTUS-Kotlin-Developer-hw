package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.app.ktor.configs.CassandraConfig
import api.kotlinproject.app.ktor.configs.ConfigPaths
import api.kotlinproject.backend.repo.cassandra.RepoMlCassandra
import api.kotlinproject.common.repo.IRepoMl
import io.ktor.server.application.*

actual fun Application.getDatabaseConf(type: MlDbType): IRepoMl {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        "cassandra", "nosql", "cass" -> initCassandra()
        else -> throw IllegalArgumentException(
            "$dbSettingPath has value of '$dbSetting', but it must be set in application.yml to one of: " +
                    "'inmemory', 'cassandra'"
        )
    }
}

private fun Application.initCassandra(): IRepoMl {
    val config = CassandraConfig(environment.config)
    return RepoMlCassandra(
        keyspaceName = config.keyspace,
        host = config.host,
        port = config.port,
        user = config.user,
        pass = config.pass,
    )
}


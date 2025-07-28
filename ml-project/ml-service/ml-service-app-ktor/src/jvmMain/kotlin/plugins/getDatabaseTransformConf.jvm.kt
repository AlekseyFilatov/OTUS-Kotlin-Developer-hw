package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.app.ktor.configs.CassandraConfig
import api.kotlinproject.app.ktor.configs.ConfigPaths
import api.kotlinproject.common.repo.IRepoMlTransform
import api.kotlinproject.repo.inmemory.MlRepoTransformInMemory
import io.ktor.server.application.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

actual fun Application.getDatabaseTransformConf(type: MlDbType): IRepoMlTransform {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initTransformInMemory()
        "cassandra", "nosql", "cass" -> initTransformCassandra()
        else -> throw IllegalArgumentException(
            "$dbSettingPath has value of '$dbSetting', but it must be set in application.yml to one of: " +
                    "'inmemory', 'cassandra'"
        )
    }
}

/*fun Application.initTransformInMemory(): IRepoMlTransform {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return MlRepoTransformInMemory(ttl = ttlSetting ?: 10.minutes)
}*/

private fun Application.initTransformCassandra(): MlRepoTransformInMemory {
    val config = CassandraConfig(environment.config)
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return MlRepoTransformInMemory(ttl = ttlSetting ?: 10.minutes)
    /*return RepoMlCassandra(
        keyspaceName = config.keyspace,
        host = config.host,
        port = config.port,
        user = config.user,
        pass = config.pass,
    )*/
}


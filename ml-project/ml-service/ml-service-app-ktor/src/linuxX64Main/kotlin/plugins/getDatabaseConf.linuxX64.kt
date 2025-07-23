package api.kotlinproject.app.ktor.plugins

import io.ktor.server.application.*
import api.kotlinproject.app.ktor.configs.ConfigPaths
import api.kotlinproject.common.repo.IRepoMl

actual fun Application.getDatabaseConf(type: MlDbType): IRepoMl {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
//        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw IllegalArgumentException(
            "$dbSettingPath has value of '$dbSetting', but it must be set in application.yml to one of: " +
                    "'inmemory'"
        )
    }
}

//fun Application.initPostgres(): IRepoAd {
//    val config = PostgresConfig(environment.config)
//    return RepoAdSql(
//        properties = SqlProperties(
//            host = config.host,
//            port = config.port,
//            user = config.user,
//            password = config.password,
//            schema = config.schema,
//            database = config.database,
//        ),
//    )
//}

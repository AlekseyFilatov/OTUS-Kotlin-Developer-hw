package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.app.ktor.configs.ConfigPaths
import api.kotlinproject.common.repo.IRepoMlTrainResult
import api.kotlinproject.common.repo.IRepoMlTransform
import io.ktor.server.application.*

actual fun Application.getDatabaseTrainResultConf(type: MlDbType): IRepoMlTrainResult {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initTrainResultInMemory()
//        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw IllegalArgumentException(
            "$dbSettingPath has value of '$dbSetting', but it must be set in application.yml to one of: " +
                    "'inmemory'"
        )
    }
}

actual fun Application.getDatabaseTransformConf(type: MlDbType): IRepoMlTransform {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initTransformInMemory()
//        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw IllegalArgumentException(
            "$dbSettingPath has value of '$dbSetting', but it must be set in application.yml to one of: " +
                    "'inmemory'"
        )
    }
}



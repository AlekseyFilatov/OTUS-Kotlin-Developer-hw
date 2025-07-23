package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.app.ktor.configs.ConfigPaths
import api.kotlinproject.common.repo.IRepoMl
import api.kotlinproject.common.repo.IRepoMlTrainResult
import api.kotlinproject.common.repo.IRepoMlTransform
import io.ktor.server.application.*

actual fun Application.getDatabaseConf(type: MlDbType): IRepoMl {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'inmemory', 'cassandra'"
        )
    }
}

actual fun Application.getDatabaseTrainResultConf(type: MlDbType): IRepoMlTrainResult {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initTrainResultInMemory()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'inmemory', 'cassandra'"
        )
    }
}

actual fun Application.getDatabaseTransformConf(type: MlDbType): IRepoMlTransform {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initTransformInMemory()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'inmemory', 'cassandra'"
        )
    }
}
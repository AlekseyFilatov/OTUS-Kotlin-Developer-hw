package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.common.repo.IRepoMlTrainResult
import api.kotlinproject.repo.inmemory.MlRepoTrainResultInMemory
import io.ktor.server.application.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

expect fun Application.getDatabaseTrainResultConf(type: MlDbType): IRepoMlTrainResult

fun Application.initTrainResultInMemory(): IRepoMlTrainResult {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return MlRepoTrainResultInMemory(ttl = ttlSetting ?: 10.minutes)
}


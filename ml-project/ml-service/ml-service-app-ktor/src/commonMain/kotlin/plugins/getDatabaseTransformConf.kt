package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.common.repo.IRepoMlTransform
import api.kotlinproject.repo.inmemory.MlRepoTransformInMemory
import io.ktor.server.application.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

expect fun Application.getDatabaseTransformConf(type: MlDbType): IRepoMlTransform

fun Application.initTransformInMemory(): IRepoMlTransform {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return MlRepoTransformInMemory(ttl = ttlSetting ?: 10.minutes)
}
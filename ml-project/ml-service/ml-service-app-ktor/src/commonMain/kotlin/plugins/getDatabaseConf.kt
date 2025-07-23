package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.common.repo.IRepoMl
import api.kotlinproject.repo.inmemory.MlRepoInMemory
import io.ktor.server.application.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

expect fun Application.getDatabaseConf(type: MlDbType): IRepoMl

enum class MlDbType(val confName: String) {
    PROD("prod"), TEST("test")
}

fun Application.initInMemory(): IRepoMl {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return MlRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}

package api.kotlinproject.app.ktor.plugins

import io.ktor.server.application.*
import api.kotlinproject.logging.common.MpLoggerProvider
import api.kotlinproject.logging.kermit.mpLoggerKermit

actual fun Application.getLoggerProviderConf(): MpLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "socket", "sock" -> getSocketLoggerProvider()
        "kmp", null -> MpLoggerProvider { mpLoggerKermit(it) }
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp and socket")
    }

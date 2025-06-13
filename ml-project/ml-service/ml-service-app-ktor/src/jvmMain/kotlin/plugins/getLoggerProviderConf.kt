package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.logging.common.MpLoggerProvider
import api.kotlinproject.logging.jvm.mpLoggerLogback
import api.kotlinproject.logging.kermit.mpLoggerKermit
import io.ktor.server.application.*

actual fun Application.getLoggerProviderConf(): MpLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "kmp" -> MpLoggerProvider { mpLoggerKermit(it) }
        "socket", "sock" -> getSocketLoggerProvider()
        "logback", null -> MpLoggerProvider { mpLoggerLogback(it) }
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp, socket and logback (default)")
}

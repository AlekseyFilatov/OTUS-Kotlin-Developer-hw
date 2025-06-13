package api.kotlinproject.app.ktor.plugins

import io.ktor.server.application.*
import api.kotlinproject.logging.common.MpLoggerProvider
import api.kotlinproject.logging.socket.SocketLoggerSettings
import api.kotlinproject.logging.socket.mpLoggerSocket

fun Application.getSocketLoggerProvider(): MpLoggerProvider {
    val loggerSettings = environment.config.config("ktor.socketLogger").let { conf ->
        SocketLoggerSettings(
            host = conf.propertyOrNull("host")?.getString() ?: "127.0.0.1",
            port = conf.propertyOrNull("port")?.getString()?.toIntOrNull() ?: 9002,
        )
    }
    return MpLoggerProvider { mpLoggerSocket(it, loggerSettings) }
}

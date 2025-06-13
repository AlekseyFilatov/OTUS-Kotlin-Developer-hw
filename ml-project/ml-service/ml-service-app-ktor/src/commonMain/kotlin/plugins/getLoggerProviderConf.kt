package api.kotlinproject.app.ktor.plugins

import io.ktor.server.application.*
import api.kotlinproject.logging.common.MpLoggerProvider

expect fun Application.getLoggerProviderConf(): MpLoggerProvider

package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.app.ktor.base.KtorWsSessionRepo
import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlCorSettings
import io.ktor.server.application.*

fun Application.initAppSettings(): MdlAppSettings {
    val corSettings = MdlCorSettings(
        loggerProvider = getLoggerProviderConf(),
        wsSessions = KtorWsSessionRepo(),
    )
    return MdlAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = MdlMlProcessor(corSettings)
    )
}

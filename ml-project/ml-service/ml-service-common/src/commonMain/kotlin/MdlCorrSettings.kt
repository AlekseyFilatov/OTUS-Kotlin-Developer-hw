package api.kotlinproject.common

import api.kotlinproject.logging.common.MpLoggerProvider
import api.kotlinproject.common.ws.IMdlWsSessionRepo

data class MdlCorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
    val wsSessions: IMdlWsSessionRepo = IMdlWsSessionRepo.NONE,
) {
    companion object {
        val NONE = MdlCorSettings()
    }
}
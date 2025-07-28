package api.kotlinproject.common

import api.kotlinproject.common.repo.IRepoMl
import api.kotlinproject.common.ws.IMdlWsSessionRepo
import api.kotlinproject.logging.common.MpLoggerProvider

data class MdlCorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
    val wsSessions: IMdlWsSessionRepo = IMdlWsSessionRepo.NONE,
    val repoStub: IRepoMl = IRepoMl.NONE,
    val repoTest: IRepoMl = IRepoMl.NONE,
    val repoProd: IRepoMl = IRepoMl.NONE,
) {
    companion object {
        val NONE = MdlCorSettings()
    }
}
package api.kotlinproject.common

import api.kotlinproject.common.repo.IRepoMlTrainResult
import api.kotlinproject.common.ws.IMdlWsSessionRepo
import api.kotlinproject.logging.common.MpLoggerProvider

data class MdlTrainResultCorrSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
    val wsTrainResultSessions: IMdlWsSessionRepo = IMdlWsSessionRepo.NONE,
    val repoStubTrainResult: IRepoMlTrainResult = IRepoMlTrainResult.NONE,
    val repoTestTrainResult: IRepoMlTrainResult = IRepoMlTrainResult.NONE,
    val repoProdTrainResult: IRepoMlTrainResult = IRepoMlTrainResult.NONE,
) {
    companion object {
        val NONE = MdlTrainResultCorrSettings()
    }
}
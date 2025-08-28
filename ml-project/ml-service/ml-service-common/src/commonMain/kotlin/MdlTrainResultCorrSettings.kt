package api.kotlinproject.common

import api.kotlinproject.common.repo.IRepoMlTrainResult
import api.kotlinproject.common.trainmodel.ITrainModelMlTrainResult
import api.kotlinproject.common.ws.IMdlWsSessionRepo
import api.kotlinproject.logging.common.MpLoggerProvider

data class MdlTrainResultCorrSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
    val wsTrainResultSessions: IMdlWsSessionRepo = IMdlWsSessionRepo.NONE,
    val repoStubTrainResult: IRepoMlTrainResult = IRepoMlTrainResult.NONE,
    val repoTestTrainResult: IRepoMlTrainResult = IRepoMlTrainResult.NONE,
    val repoProdTrainResult: IRepoMlTrainResult = IRepoMlTrainResult.NONE,
    val modelXGBoostTrainResult: ITrainModelMlTrainResult = ITrainModelMlTrainResult.NONE,
    val modelRapidsTrainResult : ITrainModelMlTrainResult = ITrainModelMlTrainResult.NONE,
    val modelForestTrainResult : ITrainModelMlTrainResult = ITrainModelMlTrainResult.NONE,
    val modelStubTrainResult : ITrainModelMlTrainResult = ITrainModelMlTrainResult.NONE
) {
    companion object {
        val NONE = MdlTrainResultCorrSettings()
    }
}
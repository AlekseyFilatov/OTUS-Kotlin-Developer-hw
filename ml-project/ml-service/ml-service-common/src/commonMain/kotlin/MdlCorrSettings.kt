package api.kotlinproject.common

import api.kotlinproject.common.repo.IRepoMl
import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.common.ws.IMdlWsSessionRepo
import api.kotlinproject.logging.common.MpLoggerProvider

data class MdlCorSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
    val wsSessions: IMdlWsSessionRepo = IMdlWsSessionRepo.NONE,
    val repoStub: IRepoMl = IRepoMl.NONE,
    val repoTest: IRepoMl = IRepoMl.NONE,
    val repoProd: IRepoMl = IRepoMl.NONE,
    val modelXGBoost : ITrainModelMl = ITrainModelMl.NONE,
    val modelRapids : ITrainModelMl = ITrainModelMl.NONE,
    val modelForest : ITrainModelMl = ITrainModelMl.NONE,
    val modelStub : ITrainModelMl = ITrainModelMl.NONE
) {
    companion object {
        val NONE = MdlCorSettings()
    }
}
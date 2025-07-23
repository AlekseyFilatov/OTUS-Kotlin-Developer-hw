package api.kotlinproject.common

import api.kotlinproject.common.repo.IRepoMlTransform
import api.kotlinproject.common.ws.IMdlWsSessionRepo
import api.kotlinproject.logging.common.MpLoggerProvider

data class MdlTransformCorrSettings(
    val loggerProvider: MpLoggerProvider = MpLoggerProvider(),
    val wsSessionsTransform: IMdlWsSessionRepo = IMdlWsSessionRepo.NONE,
    val repoStubTransform: IRepoMlTransform = IRepoMlTransform.NONE,
    val repoTestTransform: IRepoMlTransform = IRepoMlTransform.NONE,
    val repoProdTransform: IRepoMlTransform = IRepoMlTransform.NONE,
) {
    companion object {
        val NONE = MdlTransformCorrSettings()
    }
}
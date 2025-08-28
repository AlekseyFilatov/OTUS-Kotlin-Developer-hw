package api.kotlinproject.biz.validation

import api.kotlinproject.backend.repository.inmemory.MlTrainModelTrainResultStub
import api.kotlinproject.biz.MdlMlAnalyticProcessor
import api.kotlinproject.common.MdlTrainResultCorrSettings
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.repo.common.MlRepoTrainResultInitialized
import api.kotlinproject.repo.inmemory.MlRepoTrainResultInMemory
import api.kotlinproject.stubs.MdlMlTrainResultStub


abstract class BaseBizValidationAnalyticTest {
    protected abstract val command: MdlCommand
    private val repoTrainResult = MlRepoTrainResultInitialized(
        repo = MlRepoTrainResultInMemory(),
        initObjects = listOf(
            MdlMlTrainResultStub.get(),
        ),
    )
    private val model = MlTrainModelTrainResultStub()

    private val settingsAnalytic by lazy { MdlTrainResultCorrSettings(repoTestTrainResult = repoTrainResult,
        modelStubTrainResult = model, modelXGBoostTrainResult = model,
        modelRapidsTrainResult = model, modelForestTrainResult = model) }
    protected val processorAnalytic by lazy { MdlMlAnalyticProcessor(settingsAnalytic) }
}

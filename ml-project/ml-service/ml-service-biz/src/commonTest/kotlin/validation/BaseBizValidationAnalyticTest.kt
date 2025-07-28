package api.kotlinproject.biz.validation

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

    private val settingsAnalytic by lazy { MdlTrainResultCorrSettings(repoTestTrainResult = repoTrainResult) }
    protected val processorAnalytic by lazy { MdlMlAnalyticProcessor(settingsAnalytic) }
}

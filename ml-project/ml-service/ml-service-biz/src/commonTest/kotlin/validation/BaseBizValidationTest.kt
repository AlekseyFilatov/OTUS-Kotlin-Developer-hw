package api.kotlinproject.biz.validation

import api.kotlinproject.backend.repository.inmemory.MlTrainModelStub
import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.repo.common.MlRepoInitialized
import api.kotlinproject.repo.inmemory.MlRepoInMemory
import api.kotlinproject.stubs.MdlMlStub


abstract class BaseBizValidationTest {
    protected abstract val command: MdlCommand
    private val repo = MlRepoInitialized(
        repo = MlRepoInMemory(),
        initObjects = listOf(
            MdlMlStub.get(),
        ),
    )
    private val model : ITrainModelMl = MlTrainModelStub()

    //private val settings by lazy { MdlCorSettings() }
    private val settings by lazy { MdlCorSettings(repoTest = repo,
        modelStub = model, modelXGBoost = model,
        modelRapids = model, modelForest = model) }
    protected val processor by lazy { MdlMlProcessor(settings) }
}

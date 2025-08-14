package api.kotlinproject.biz.validation

import api.kotlinproject.backend.repository.inmemory.MlTrainModelTransformStub
import api.kotlinproject.biz.MdlMlTransformProcessor
import api.kotlinproject.common.MdlTransformCorrSettings
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.repo.common.MlRepoTransformInitialized
import api.kotlinproject.repo.inmemory.MlRepoTransformInMemory
import api.kotlinproject.stubs.MdlMlTransformStub


abstract class BaseBizValidationTransformTest {
    protected abstract val command: MdlCommand
    private val repoTransform = MlRepoTransformInitialized(
        repo = MlRepoTransformInMemory(),
        initObjects = listOf(
            MdlMlTransformStub.get(),
        ),
    )
    private val model = MlTrainModelTransformStub()

    private val settingsTransform by lazy { MdlTransformCorrSettings(repoTestTransform= repoTransform,
        modelStubTransform = model,
        modelForestTransform = model,
        modelRapidsTransform = model,
        modelXGBoostTransform = model
        ) }
    protected val processorTransform by lazy { MdlMlTransformProcessor(settingsTransform) }
}
package api.kotlinproject.app.ktor.repo

import api.kotlinproject.api.v1.models.MlRequestDebugMode
import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.backend.repository.inmemory.MlTrainModelStub
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.common.repo.IRepoMl
import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.repo.common.MlRepoInitialized
import api.kotlinproject.repo.inmemory.MlRepoInMemory

class V2MlRepoInmemoryTest : V2MlRepoBaseTest() {
    override val workMode: MlRequestDebugMode = MlRequestDebugMode.TEST
    private fun mkAppSettings(repo: IRepoMl, model : ITrainModelMl) = MdlAppSettings(
        corSettings = MdlCorSettings(
            repoTest = repo,
            modelXGBoost = model,
            modelForest = model,
            modelRapids = model
        )
    )

    override val appSettingsCreate: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(MlRepoInMemory(randomUuid = { uuidNew }))
        ,model= MlTrainModelStub()
    )
    override val appSettingsRead: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(
            MlRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initMl),
        ),model= MlTrainModelStub()
    )
    override val appSettingsUpdate: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(
            MlRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initMl),
        ),model= MlTrainModelStub()
    )
    override val appSettingsDelete: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(
            MlRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initMl),
        ),model= MlTrainModelStub()
    )
    override val appSettingsSearch: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(
            MlRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initMl),
        ),model= MlTrainModelStub()
    )

}

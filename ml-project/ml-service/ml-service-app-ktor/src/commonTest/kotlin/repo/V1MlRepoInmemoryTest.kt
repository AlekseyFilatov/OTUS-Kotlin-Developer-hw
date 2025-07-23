package api.kotlinproject.app.ktor.repo

import api.kotlinproject.api.v1.models.MlRequestDebugMode
import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.common.repo.IRepoMl
import api.kotlinproject.repo.common.MlRepoInitialized
import api.kotlinproject.repo.inmemory.MlRepoInMemory

class V2MlRepoInmemoryTest : V2MlRepoBaseTest() {
    override val workMode: MlRequestDebugMode = MlRequestDebugMode.TEST
    private fun mkAppSettings(repo: IRepoMl) = MdlAppSettings(
        corSettings = MdlCorSettings(
            repoTest = repo
        )
    )

    override val appSettingsCreate: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(MlRepoInMemory(randomUuid = { uuidNew }))
    )
    override val appSettingsRead: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(
            MlRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initMl),
        )
    )
    override val appSettingsUpdate: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(
            MlRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initMl),
        )
    )
    override val appSettingsDelete: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(
            MlRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initMl),
        )
    )
    override val appSettingsSearch: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(
            MlRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initMl),
        )
    )

}

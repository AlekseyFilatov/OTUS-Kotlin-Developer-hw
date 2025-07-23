package api.kotlinproject.repo.common

import api.kotlinproject.common.models.MdlMlTrainResult

/**
 * Делегат для всех репозиториев, позволяющий инициализировать базу данных предзагруженными данными
 */
class MlRepoTrainResultInitialized(
    val repo: IRepoMlTrainResultInitializable,
    initObjects: List<MdlMlTrainResult> = emptyList(),
) : IRepoMlTrainResultInitializable by repo {
    @Suppress("unused")
    val initializedObjects: List<MdlMlTrainResult> = save(initObjects).toList()
}
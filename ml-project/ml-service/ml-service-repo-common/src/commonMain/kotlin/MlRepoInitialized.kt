package api.kotlinproject.repo.common

import api.kotlinproject.common.models.MdlMl

/**
 * Делегат для всех репозиториев, позволяющий инициализировать базу данных предзагруженными данными
 */
class MlRepoInitialized(
    val repo: IRepoMlInitializable,
    initObjects: Collection<MdlMl> = emptyList(),
) : IRepoMlInitializable by repo {
    @Suppress("unused")
    val initializedObjects: List<MdlMl> = save(initObjects).toList()
}

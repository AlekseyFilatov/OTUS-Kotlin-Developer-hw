package api.kotlinproject.repo.common

import api.kotlinproject.common.models.MdlMlTransform

class MlRepoTransformInitialized(
    val repo: IRepoMlTransformInitializable,
    initObjects: Collection<MdlMlTransform> = emptyList(),
) : IRepoMlTransformInitializable by repo {
    @Suppress("unused")
    val initializedObjects: List<MdlMlTransform> = save(initObjects).toList()
}
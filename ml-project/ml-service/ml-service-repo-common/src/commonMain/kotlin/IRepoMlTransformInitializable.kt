package api.kotlinproject.repo.common

import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.repo.IRepoMlTransform

interface IRepoMlTransformInitializable: IRepoMlTransform {
    fun save(mls: Collection<MdlMlTransform>) : Collection<MdlMlTransform>
}
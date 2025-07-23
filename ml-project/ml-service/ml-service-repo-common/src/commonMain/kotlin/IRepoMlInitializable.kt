package api.kotlinproject.repo.common

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.repo.IRepoMl

interface IRepoMlInitializable: IRepoMl {
    fun save(mls: Collection<MdlMl>) : Collection<MdlMl>
}

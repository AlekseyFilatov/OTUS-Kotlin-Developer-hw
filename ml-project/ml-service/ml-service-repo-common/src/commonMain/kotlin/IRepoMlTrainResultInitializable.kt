package api.kotlinproject.repo.common

import api.kotlinproject.common.models.MdlMlTrainResult
import api.kotlinproject.common.repo.IRepoMlTrainResult

interface IRepoMlTrainResultInitializable: IRepoMlTrainResult {
    fun save(mls: Collection<MdlMlTrainResult>) : Collection<MdlMlTrainResult>
}

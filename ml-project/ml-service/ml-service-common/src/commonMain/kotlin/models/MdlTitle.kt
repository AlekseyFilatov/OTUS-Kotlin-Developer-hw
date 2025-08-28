package api.kotlinproject.common.models

import api.kotlinproject.common.trainmodel.ITrainModelMl

/*enum class MdlTitle {
    FOREST,
    XGBOOST,
    RAPIDS,
    STUB,
    NONE
}*/
data class MdlTitle(
    var modelXGBoost: ITrainModelMl = ITrainModelMl.NONE,
    var modelForest: ITrainModelMl = ITrainModelMl.NONE,
    var modelRapids: ITrainModelMl = ITrainModelMl.NONE,
) {
    companion object {
        var NONE = MdlTitle()
    }
}


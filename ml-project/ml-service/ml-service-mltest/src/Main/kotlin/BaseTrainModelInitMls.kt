package api.kotlinproject.mlmodel.mlxgboost

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTitle

abstract class BaseTrainModelInitMls(private val op: String): IInitTrainObjects<MdlMl> {
    fun createInitTestModel(
        suf: String,
    ) = MdlMl(
        id = MdlMlId("ml-train-$op-$suf"),
        title = MdlMlTitle("ml-train-$op-$suf").asString(),
        description = "$suf stub description",
    )
}
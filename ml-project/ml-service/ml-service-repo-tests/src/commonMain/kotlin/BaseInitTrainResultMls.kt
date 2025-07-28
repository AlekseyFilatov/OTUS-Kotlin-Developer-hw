package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTrainResult

abstract class BaseInitTrainResultMls(private val op: String): IInitObjects<MdlMlTrainResult> {
    fun createInitTestModel(
        suf: String,
    ) = MdlMlTrainResult(
        id = MdlMlId("ml-repo-$op-$suf"),
        dateTime  = "",
        close = 0.0,
        labelDatetime = "",
        realResult = 0.0,
        prediction = 0.0,
        error = 0.0
    )
}
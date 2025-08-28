package api.kotlinproject.common.trainmodel.exceptions

import api.kotlinproject.common.models.MdlMlId

open class TrainModelMlException (
    @Suppress("unused")
    val mlId: MdlMlId,
    msg: String,
): TrainModelException(msg)
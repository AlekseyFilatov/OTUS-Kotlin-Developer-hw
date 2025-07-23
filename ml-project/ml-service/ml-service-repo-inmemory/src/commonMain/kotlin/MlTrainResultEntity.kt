package api.kotlinproject.repo.inmemory

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTrainResult

data class MlTrainResultEntity(
    val dateTime: String? = null,
    val close: String? = null,
    val labelDatetime: String? = null,
    val realResult: String? = null,
    val prediction: String? = null,
    val error: String? = null,
    val id: String? = null
) {
    constructor(model: MdlMlTrainResult): this(
        dateTime = model.dateTime,
        close = model.close.toString(),
        labelDatetime = model.labelDatetime,
        realResult = model.realResult.toString(),
        prediction = model.prediction.toString(),
        error = model.error.toString(),
        id = model.id.asString().takeIf { it.isNotBlank() },
    )

    fun toInternal() = MdlMlTrainResult(
        id = id?.let { MdlMlId(it) }?: MdlMlId.NONE,
        dateTime = dateTime.toString(),
        close = close?.toDouble() ?: 0.0,
        labelDatetime = labelDatetime.toString(),
        realResult = realResult?.toDouble() ?: 0.0,
        prediction = prediction?.toDouble() ?: 0.0,
        error = error?.toDouble() ?: 0.0,
    )
}

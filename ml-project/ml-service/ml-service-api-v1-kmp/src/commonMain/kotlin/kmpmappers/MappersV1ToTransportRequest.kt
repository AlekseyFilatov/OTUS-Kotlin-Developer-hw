package api.kotlinproject.mappers.kmpmappers

import api.kotlinproject.api.v1.models.*
import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlAnalytic


fun MdlMl.toTransportCreateMl() = MlCreateObject(
    title = title,
    description = description
    )

fun MdlMl.toTransportReadMl() = MlReadObject(
    title = title
)

fun MdlMl.toTransportUpdateMl() = MlUpdateObject(
    title = title
)

fun MdlMl.toTransportDeleteMl() = MlDeleteObject(
    title = title
)

fun MdlMlAnalytic.toTransportAnalyticMl() = AnalyticMl(
    ticker = ticker.asString(),
    taskNumber = taskNumber as String?,
    dateStart = dateStart,
    dateEnd = dateEnd,
    evalPivotPoint = evalPivotPoint,
    modelParameters = ModelParameters(),
    dateOffset = dateOffset,
    batchSize = batchSize as Int?
)
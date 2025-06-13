package api.kotlinproject.mappers.kmpmappers

import api.kotlinproject.api.v1.models.*
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.exceptions.UnknownMdlCommand
import api.kotlinproject.common.models.*

fun MdlContext.toTransportMl() :IResponse = when (val cmd = command) {
    MdlCommand.CREATE -> toTransportCreate()
    MdlCommand.READ -> toTransportRead()
    MdlCommand.UPDATE -> toTransportUpdate()
    MdlCommand.DELETE -> toTransportDelete()
    MdlCommand.SEARCH -> toTransportSearch()
    MdlCommand.ANALITYCML -> toTransportAnalytic()
    MdlCommand.TRANSFORMML -> toTransportTransform()
    MdlCommand.INIT -> toTransportInit()
    MdlCommand.FINISH -> throw UnknownMdlCommand(cmd)
    MdlCommand.NONE -> throw UnknownMdlCommand(cmd)
}

fun MdlContext.toTransportAnalytic() = AnalyticMlReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ml = mlResponseTrainModel.toTransportMl()
)

fun MdlContext.toTransportInit() = MlInitResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
)

fun MdlContext.toTransportTransform() = TransformMlUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ml = mlTransformMl.toTransportMl()
)

fun MdlContext.toTransportCreate() = MlCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ml = mlResponse.toTransportMl()
)

fun MdlContext.toTransportRead() = MlReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ml = mlResponse.toTransportMl()
)

fun MdlContext.toTransportUpdate() = MlUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ml = mlResponse.toTransportMl()
)

fun MdlContext.toTransportDelete() = MlDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ml = mlResponse.toTransportMl()
)

fun MdlContext.toTransportSearch() = MlSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    mls = mlsResponse.toTransportMl()
)

fun MdlMlTrainResult.toTransportMl() = TrainResultMl(
        dateTime = dateTime,
        close = close,
        labelDatetime = labelDatetime,
        realResult = realResult,
        prediction = prediction,
        error = error
)


fun MdlMlTransform.toTransportMl() = TransformMl(
   ticker = ticker,
    taskNumber = taskNumber,
    dateStart = dateStart,
    dateEnd = dateEnd,
    dateOffset = dateOffset,
    batchSize = batchSize
)

fun List<MdlMl>.toTransportMl(): List<MlResponseObject>? = this
    .map { it.toTransportMl() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun MdlMl.toTransportMl(): MlResponseObject = MlResponseObject(
    title = title?.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() }
)

private fun List<MdlError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportMl() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MdlError.toTransportMl() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

internal fun MdlMlTitle.toTransportMl() = takeIf { it != MdlMlTitle("") }

private fun MdlState.toResult(): ResponseResult? = when (this) {
    MdlState.RUNNING -> ResponseResult.SUCCESS
    MdlState.FAILING -> ResponseResult.ERROR
    MdlState.FINISHING -> ResponseResult.SUCCESS
    MdlState.NONE -> null
}

package api.kotlinproject.mappers.v1.jacksonmappers

import api.kotlinproject.api.jackson.v1.models.*
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.exceptions.UnknownMdlCommand
import api.kotlinproject.common.models.*

fun MdlContext.toTransportMl(): IResponse = when (val cmd = command) {
    MdlCommand.CREATE -> toTransportCreate()
    MdlCommand.READ -> toTransportRead()
    MdlCommand.UPDATE -> toTransportUpdate()
    MdlCommand.DELETE -> toTransportDelete()
    MdlCommand.SEARCH -> toTransportSearch()
    MdlCommand.ANALITYCML -> toTransportAnalytic()
    MdlCommand.TRANSFORMML -> toTransportTransform()
    MdlCommand.INIT -> toTransportInit()
    MdlCommand.FINISH -> object: IResponse {
        override val responseType: String? = null
        //override val responseTitle: String? = null
        override val result: ResponseResult? = null
        override val errors: List<Error>? = null
    }
    MdlCommand.NONE -> throw UnknownMdlCommand(cmd)
}

fun MdlContext.toTransportInit() = MlInitResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
)

fun MdlContext.toTransportAnalytic() = AnalyticMlReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ml = mlResponseTrainResult.toTransportMl()
)

fun MdlContext.toTransportTransform() = TransformMlUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ml = mlTransformMl.toTransportMl()
)

fun MdlMlTrainResult.toTransportMl() = TrainResultMl(
        dateTime = dateTime,
        close = close.toBigDecimal(),
        labelDatetime = labelDatetime,
        realResult = realResult.toBigDecimal(),
        prediction = prediction.toBigDecimal(),
        error = error.toBigDecimal(),
        id = id.asString()
)

fun MdlMlTransform.toTransportMl() = TransformMl(
    ticker = ticker,
    taskNumber = taskNumber,
    dateStart = dateStart,
    dateEnd = dateEnd,
    dateOffset = dateOffset,
    batchSize = batchSize,
    id = id.asString(),
    title = title.asString()
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
    mls = mlsResponse.toTransportMls()
)

fun List<MdlMl>.toTransportMls(): List<MlResponseObject>? = this
    .map { it.toTransportMl() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun MdlMl.toTransportMl(): MlResponseObject = MlResponseObject(
    title = title?.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() }
)

internal fun MdlMlTitle.toTransportMl() = takeIf { it != MdlMlTitle.NONE }?.asString()


internal fun List<MdlError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportMl() }
    .toList()
    .takeIf { it.isNotEmpty() }

internal fun MdlError.toTransportMl() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

internal fun MdlState.toResult(): ResponseResult? = when (this) {
    MdlState.RUNNING -> ResponseResult.SUCCESS
    MdlState.FAILING -> ResponseResult.ERROR
    MdlState.FINISHING -> ResponseResult.SUCCESS
    MdlState.NONE -> null
}

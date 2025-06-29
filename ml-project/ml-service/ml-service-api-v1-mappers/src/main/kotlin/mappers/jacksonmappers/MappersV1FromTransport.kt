package api.kotlinproject.mappers.v1.jacksonmappers

import api.kotlinproject.api.jackson.v1.models.*
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.*
import api.kotlinproject.common.stubs.MdlMlTrainResultStubs
import api.kotlinproject.common.stubs.MdlStubs
import api.kotlinproject.mappers.v1.jacksonmappers.exceptions.UnknownRequestClass

fun MdlContext.fromTransport(request: IRequest) = when (request) {
    is MlCreateRequest -> fromTransport(request)
    is MlReadRequest -> fromTransport(request)
    is MlUpdateRequest -> fromTransport(request)
    is MlDeleteRequest -> fromTransport(request)
    is MlSearchRequest -> fromTransport(request)
    is AnalyticMlReadRequest -> fromTransport(request)
    is TransformMlUpdateRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun TransformMl?.toInternal(): MdlMlTransform = if (this != null) {
    MdlMlTransform(
        ticker = ticker,
        taskNumber = taskNumber,
        dateStart = dateStart,
        dateEnd = dateEnd,
        dateOffset = dateOffset,
        batchSize = batchSize
    )
} else {
    MdlMlTransform()
}

fun MdlContext.fromTransport(request: TransformMlUpdateRequest) {
    command = MdlCommand.TRANSFORMML
    mlTransformMl = request.ml.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

//private fun String?.toAdId() = this?.let { MkplAdId(it) } ?: MkplAdId.NONE
//private fun String?.toAdWithId() = MkplAd(id = this.toAdId())
//private fun String?.toAdLock() = this?.let { MkplAdLock(it) } ?: MkplAdLock.NONE


private fun MlDebug?.transportToWorkMode(): MdlWorkMode = when (this?.mode) {
    MlRequestDebugMode.PROD -> MdlWorkMode.PROD
    MlRequestDebugMode.TEST -> MdlWorkMode.TEST
    MlRequestDebugMode.STUB -> MdlWorkMode.STUB
    null -> MdlWorkMode.PROD
}

private fun MlDebug?.transportToStubCase(): MdlStubs = when (this?.stub) {
    MlRequestDebugStubs.SUCCESS -> MdlStubs.SUCCESS
    MlRequestDebugStubs.NOT_FOUND -> MdlStubs.NOT_FOUND
    MlRequestDebugStubs.ERROR -> MdlStubs.ERROR
    null -> MdlStubs.NONE
}

fun MdlContext.fromTransport(request: MlCreateRequest) {
    command = MdlCommand.CREATE
    mlRequest = request.ml?.toInternal() ?: MdlMl()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MdlContext.fromTransport(request: AnalyticMlReadRequest) {
    command = MdlCommand.ANALITYCML
    mlAnalyticMl = request.ml.toInternal()
    mlResponseTrainModel = MdlMlTrainResultStubs.ML_TrainResult
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MdlContext.fromTransport(request: MlReadRequest) {
    command = MdlCommand.READ
    mlRequest = request.ml.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun MlReadObject?.toInternal(): MdlMl = if (this != null) {
    MdlMl(title = title)
} else {
    MdlMl()
}

private fun AnalyticMl?.toInternal(): MdlMlAnalytic = if (this != null) {
    MdlMlAnalytic(
        ticker = MdlMlTicker(ticker.toString()),
        taskNumber = MdlMlTaskNumber(taskNumber.toString()),
        dateStart = dateStart,
        dateEnd = dateEnd,
        evalPivotPoint = evalPivotPoint,
        dateOffset = dateOffset,
        modelParameters = MdlMlModelParameters()
    )
} else {
    MdlMlAnalytic()
}


fun MdlContext.fromTransport(request: MlUpdateRequest) {
    command = MdlCommand.UPDATE
    mlRequest = request.ml?.toInternal() ?: MdlMl()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MdlContext.fromTransport(request: MlDeleteRequest) {
    command = MdlCommand.DELETE
    mlRequest = request.ml.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun MlDeleteObject?.toInternal(): MdlMl = if (this != null) {
    MdlMl(
        title = title
    )
} else {
    MdlMl()
}

fun MdlContext.fromTransport(request: MlSearchRequest) {
    command = MdlCommand.SEARCH
    mlFilterRequest = request.mlFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun MlSearchFilter?.toInternal(): MdlMlFilter = MdlMlFilter(
    searchString = this?.searchString ?: ""
)

private fun MlCreateObject.toInternal(): MdlMl = MdlMl(
    title = this.title ?: "",
    description = this.description ?: ""
)

private fun MlUpdateObject.toInternal(): MdlMl = MdlMl(
    title = this.title ?: ""
)




package api.kotlinproject.mappers.v1

import api.kotlinproject.api.v1.models.*
import api.kotlinproject.common.MkplContext
import api.kotlinproject.common.models.*
import api.kotlinproject.common.stubs.MkplStubs
import api.kotlinproject.mappers.v1.exceptions.UnknownRequestClass


fun MkplContext.fromTransport(request: IRequest) = when (request) {
    is MlCreateRequest -> fromTransport(request)
    is MlReadRequest -> fromTransport(request)
    is MlUpdateRequest -> fromTransport(request)
    is MlDeleteRequest -> fromTransport(request)
    is MlSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

//private fun String?.toMlId() = this?.let { MkplMlId(it) } ?: MkplMlId.NONE
//private fun String?.toMlWithId() = MkplMl(id = this.toMlId())
//private fun String?.toMlLock() = this?.let { MkplMlLock(it) } ?: MkplMlLock.NONE

private fun MlDebug?.transportToWorkMode(): MkplWorkMode = when (this?.mode) {
    MlRequestDebugMode.PROD -> MkplWorkMode.PROD
    MlRequestDebugMode.TEST -> MkplWorkMode.TEST
    MlRequestDebugMode.STUB -> MkplWorkMode.STUB
    null -> MkplWorkMode.PROD
}

private fun MlDebug?.transportToStubCase(): MkplStubs = when (this?.stub) {
    MlRequestDebugStubs.SUCCESS -> MkplStubs.SUCCESS
    MlRequestDebugStubs.NOT_FOUND -> MkplStubs.NOT_FOUND
    MlRequestDebugStubs.ERROR -> MkplStubs.ERROR
    null -> MkplStubs.NONE
}

fun MkplContext.fromTransport(request: MlCreateRequest) {
    command = MkplCommand.CREATE
    mlRequest = request.ml?.toInternal() ?: MkplMl()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: MlReadRequest) {
    command = MkplCommand.READ
    mlRequest = request.ml.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun MlReadObject?.toInternal(): MkplMl = if (this != null) {
    MkplMl(
        title = title,
        description = "")
} else {
    MkplMl()
}


fun MkplContext.fromTransport(request: MlUpdateRequest) {
    command = MkplCommand.UPDATE
    mlRequest = request.ml?.toInternal() ?: MkplMl()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun MkplContext.fromTransport(request: MlDeleteRequest) {
    command = MkplCommand.DELETE
    mlRequest = request.ml.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun MlDeleteObject?.toInternal(): MkplMl = if (this != null) {
    MkplMl(
        title = title
    )
} else {
    MkplMl()
}

fun MkplContext.fromTransport(request: MlSearchRequest) {
    command = MkplCommand.SEARCH
    mlFilterRequest = request.mlFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun MlSearchFilter?.toInternal(): MkplMlFilter = MkplMlFilter(
    searchString = this?.searchString ?: ""
)

private fun MlCreateObject.toInternal(): MkplMl = MkplMl(
    title = title,
    description = this.description ?: ""
)

private fun MlUpdateObject.toInternal(): MkplMl = MkplMl(
    title = this.title
)

private fun String?.toMlTitle() = this?.let { MkplMlTitle(it) } ?: MkplMlTitle.NONE



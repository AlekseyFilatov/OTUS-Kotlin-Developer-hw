package api.kotlinproject.mappers.v1

import api.kotlinproject.api.v1.models.*
import api.kotlinproject.common.MkplContext
import api.kotlinproject.common.exceptions.UnknownMkplCommand
import api.kotlinproject.common.models.*

fun MkplContext.toTransportMl() :IResponse = when (val cmd = command) {
    MkplCommand.CREATE -> toTransportCreate()
    MkplCommand.READ -> toTransportRead()
    MkplCommand.UPDATE -> toTransportUpdate()
    MkplCommand.DELETE -> toTransportDelete()
    MkplCommand.SEARCH -> toTransportSearch()
    MkplCommand.NONE -> throw UnknownMkplCommand(cmd)
}

fun MkplContext.toTransportCreate() = MlCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ml = mlResponse.toTransportMl()
)

fun MkplContext.toTransportRead() = MlReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ml = mlResponse.toTransportMl()
)

fun MkplContext.toTransportUpdate() = MlUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ml = mlResponse.toTransportMl()
)

fun MkplContext.toTransportDelete() = MlDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ml = mlResponse.toTransportMl()
)

fun MkplContext.toTransportSearch() = MlSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    mls = mlsResponse.toTransportMl()
)

fun List<MkplMl>.toTransportMl(): List<MlResponseObject>? = this
    .map { it.toTransportMl() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun MkplMl.toTransportMl(): MlResponseObject = MlResponseObject(
    title = title?.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() }
)

private fun List<MkplError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportMl() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun MkplError.toTransportMl() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

internal fun MkplMlTitle.toTransportMl() = takeIf { it != MkplMlTitle("") }

private fun MkplState.toResult(): ResponseResult? = when (this) {
    MkplState.RUNNING -> ResponseResult.SUCCESS
    MkplState.FAILING -> ResponseResult.ERROR
    MkplState.FINISHING -> ResponseResult.SUCCESS
    MkplState.NONE -> null
}

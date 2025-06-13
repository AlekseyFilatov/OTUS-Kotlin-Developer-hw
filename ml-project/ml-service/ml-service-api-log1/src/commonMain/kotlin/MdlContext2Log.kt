package api.kotlinproject.api.log1.mapper

import api.kotlinproject.api.log1.models.*
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlFilter
import api.kotlinproject.common.models.MdlRequestTitle
import kotlinx.datetime.Clock

fun MdlContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "ml-service",
    ml = toMdlLog(),
    errors = errors.map { it.toLog() },
)

private fun MdlContext.toMdlLog(): MdlLogModel? {
    val mlNone = MdlMl()
    return MdlLogModel(
        requestTitle = requestTitle.takeIf { it != MdlRequestTitle.NONE }?.asString(),
        requestMl = mlRequest.takeIf { it != mlNone }?.toLog(),
        responseMl = mlResponse.takeIf { it != mlNone }?.toLog(),
        responseMls = mlsResponse.takeIf { it.isNotEmpty() }?.filter { it != mlNone }?.map { it.toLog() },
        requestFilter = mlFilterRequest.takeIf { it != MdlMlFilter() }?.toLog(),
    ).takeIf { it != MdlLogModel() }
}

private fun MdlMlFilter.toLog() = MlFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() }
)

private fun MdlError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name
)

private fun MdlMl.toLog() = MlLog(
    title = title?.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() }
)

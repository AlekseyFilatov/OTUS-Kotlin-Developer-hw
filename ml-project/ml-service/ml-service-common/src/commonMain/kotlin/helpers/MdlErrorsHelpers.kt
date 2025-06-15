package api.kotlinproject.common.helpers

import api.kotlinproject.common.models.MdlError

fun Throwable.asMdlError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = MdlError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

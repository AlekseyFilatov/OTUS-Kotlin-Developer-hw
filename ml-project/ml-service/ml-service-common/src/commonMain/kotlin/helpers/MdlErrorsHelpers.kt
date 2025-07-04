package api.kotlinproject.common.helpers

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.logging.common.LogLevel

fun Throwable.asMdlError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = MdlError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this
)

inline fun MdlContext.addError(vararg error: MdlError) = errors.addAll(error)

inline fun MdlContext.fail(error: MdlError) {
    addError(error)
    state = MdlState.FAILING
}

inline fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: LogLevel = LogLevel.ERROR,
) = MdlError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)
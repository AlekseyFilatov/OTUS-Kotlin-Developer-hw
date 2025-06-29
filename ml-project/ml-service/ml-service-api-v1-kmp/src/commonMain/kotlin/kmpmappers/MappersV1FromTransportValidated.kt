package api.kotlinproject.api.v1.kmpmappers

import api.kotlinproject.api.v1.models.MlCreateRequest
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.stubs.MdlStubs

// Демонстрация форматной валидации в мапере
private sealed interface Result<T,E>
private data class Ok<T,E>(val value: T) : Result<T,E>
private data class Err<T,E>(val errors: List<E>) : Result<T,E> {
    constructor(error: E) : this(listOf(error))
}

private fun <T,E> Result<T,E>.getOrExec(default: T, block: (Err<T,E>) -> Unit = {}): T = when (this) {
    is Ok<T,E> -> this.value
    is Err<T,E> -> {
        block(this)
        default
    }
}

@Suppress("unused")
private fun <T,E> Result<T,E>.getOrNull(block: (Err<T,E>) -> Unit = {}): T? = when (this) {
    is Ok<T,E> -> this.value
    is Err<T,E> -> {
        block(this)
        null
    }
}

private fun String?.transportToStubCaseValidated(): Result<MdlStubs,MdlError> = when (this) {
    "success" -> Ok(MdlStubs.SUCCESS)
    "notFound" -> Ok(MdlStubs.NOT_FOUND)
    "badTitle" -> Ok(MdlStubs.BAD_TITLE)
    "badDescription" -> Ok(MdlStubs.BAD_DESCRIPTION)
    "badSearchString" -> Ok(MdlStubs.BAD_SEARCH_STRING)
    null -> Ok(MdlStubs.NONE)
    else -> Err(
        MdlError(
            code = "wrong-stub-case",
            group = "mapper-validation",
            field = "debug.stub",
            message = "Unsupported value for case \"$this\""
        )
    )
}

@Suppress("unused")
fun MdlContext.fromTransportValidated(request: MlCreateRequest) {
    command = MdlCommand.CREATE
    // Вся магия здесь!
    stubCase = request
        .debug
        ?.stub
        ?.value
        .transportToStubCaseValidated()
        .getOrExec(MdlStubs.NONE) { err: Err<MdlStubs,MdlError> ->
            errors.addAll(err.errors)
            state = MdlState.FAILING
        }
}
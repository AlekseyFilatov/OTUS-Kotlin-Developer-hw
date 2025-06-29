package api.kotlinproject.biz.stubs

import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.stubs.MdlStubs

fun ICorChainDsl<MdlContext>.stubValidationBadTitle(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для заголовка
    """.trimIndent()

    on { stubCase == MdlStubs.BAD_TITLE && state == MdlState.RUNNING }
    handle {
        fail(
            MdlError(
                group = "validation",
                code = "validation-title",
                field = "title",
                message = "Wrong title field"
            )
        )
    }
}

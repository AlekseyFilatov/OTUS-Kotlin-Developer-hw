package api.kotlinproject.biz.stubs

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.stubs.MdlStubs
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.stubValidationBadDescription(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для описания модели
    """.trimIndent()
    on { stubCase == MdlStubs.BAD_DESCRIPTION && state == MdlState.RUNNING }
    handle {
        fail(
            MdlError(
                group = "validation",
                code = "validation-description",
                field = "description",
                message = "Wrong description field"
            )
        )
    }
}

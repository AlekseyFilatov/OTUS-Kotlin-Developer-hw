package biz.stubs

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.stubs.MdlStubs
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.stubValidationBadTransform(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для повторной тренировки модели
    """.trimIndent()
    on { stubCase == MdlStubs.BAD_TRANSFORM && state == MdlState.RUNNING }
    handle {
        fail(
            MdlError(
                group = "validation",
                code = "validation-transform",
                field = "transform",
                message = "Wrong transform fields"
            )
        )
    }
}
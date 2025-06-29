package biz.stubs

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.stubs.MdlStubs
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.stubValidationBadAnalytic(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для тренировки модели
    """.trimIndent()
    on { stubCase == MdlStubs.BAD_ANALYTIC && state == MdlState.RUNNING }
    handle {
        fail(
            MdlError(
                group = "validation",
                code = "validation-analytic",
                field = "analytic",
                message = "Wrong analytic fields"
            )
        )
    }
}
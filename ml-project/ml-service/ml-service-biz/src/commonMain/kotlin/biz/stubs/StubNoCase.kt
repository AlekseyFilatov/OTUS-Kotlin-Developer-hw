package api.kotlinproject.biz.stubs

import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlState

fun ICorChainDsl<MdlContext>.stubNoCase(title: String) = worker {
    this.title = title
    this.description = """
        Валидируем ситуацию, когда запрошен кейс, который не поддерживается в стабах
    """.trimIndent()
    on { state == MdlState.RUNNING }
    handle {
        fail(
            MdlError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}

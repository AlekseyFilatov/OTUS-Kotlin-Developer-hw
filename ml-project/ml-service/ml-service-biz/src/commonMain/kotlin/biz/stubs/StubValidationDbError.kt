package api.kotlinproject.biz.stubs

import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlError
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.stubs.MdlStubs

fun ICorChainDsl<MdlContext>.stubDbError(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки базы данных
    """.trimIndent()
    on { stubCase == MdlStubs.DB_ERROR && state == MdlState.RUNNING }
    handle {
        fail(
            MdlError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}

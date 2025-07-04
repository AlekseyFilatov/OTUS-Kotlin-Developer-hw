package api.kotlinproject.biz.validation

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.chain

fun ICorChainDsl<MdlContext>.validation(block: ICorChainDsl<MdlContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == MdlState.RUNNING }
}

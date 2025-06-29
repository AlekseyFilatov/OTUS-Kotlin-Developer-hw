package api.kotlinproject.biz.stubs

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.chain

fun ICorChainDsl<MdlContext>.stubs(title: String, block: ICorChainDsl<MdlContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == MdlWorkMode.STUB && state == MdlState.RUNNING }
}

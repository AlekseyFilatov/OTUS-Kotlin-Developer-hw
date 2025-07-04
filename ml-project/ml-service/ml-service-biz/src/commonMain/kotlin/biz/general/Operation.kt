package api.kotlinproject.biz.general

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.chain

fun ICorChainDsl<MdlContext>.operation(
    title: String,
    command: MdlCommand,
    block: ICorChainDsl<MdlContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == MdlState.RUNNING }
}

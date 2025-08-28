package api.kotlinproject.biz.trainmodel

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.chain

fun ICorChainDsl<MdlContext>.training(block: ICorChainDsl<MdlContext>.() -> Unit) = chain {
    block()
    title = "Тренировка"

    on { state == MdlState.RUNNING }
}
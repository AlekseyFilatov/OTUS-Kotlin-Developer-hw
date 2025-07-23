package biz.trainmodel

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.transformModel(title: String) = worker {
    this.title = title
    description = "Трансформация модели перед сохранением в БД"
    on { state == MdlState.RUNNING }
    handle {
        val request = mlValidatedTransform
        mlTransformMl = mlValidatedTransform
    }
}
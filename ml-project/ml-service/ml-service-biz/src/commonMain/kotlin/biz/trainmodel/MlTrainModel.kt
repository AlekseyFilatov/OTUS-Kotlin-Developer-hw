package biz.trainmodel

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.trainModel(title: String) = worker {
    this.title = title
    description = "Тренировка модели перед сохранением в БД"
    on { state == MdlState.RUNNING }
    handle {
        val request = mlResponseValidatedTrainModel
        mlTrainResultMl = mlResponseValidatedTrainModel
    }
}
package api.kotlinproject.biz.trainmodel

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.trainmodel.TrainModelMlRequest
import api.kotlinproject.common.trainmodel.TrainModelMlResponseErr
import api.kotlinproject.common.trainmodel.TrainModelMlResponseErrWithData
import api.kotlinproject.common.trainmodel.TrainModelMlResponseOk
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.trainModel(title: String) = worker {
    this.title = title
    description = "Тренировка модели перед сохранением в БД"
    on { state == MdlState.RUNNING }
    handle {
        when(val result = mlTrainModel.usingmodelMl(rq = TrainModelMlRequest(mlValidated.deepCopy()))) {
                is TrainModelMlResponseOk -> mlTrainModelResultDone = result.data
                is TrainModelMlResponseErr -> fail(result.errors)
                is TrainModelMlResponseErrWithData -> {
                    fail(result.errors)
                    mlTrainModelResultDone = result.data
                }
            }
    }
}
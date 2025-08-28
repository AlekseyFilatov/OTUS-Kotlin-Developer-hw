package api.kotlinproject.biz.trainmodel


import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.trainmodel.TrainModelMlTrainResultRequest
import api.kotlinproject.common.trainmodel.TrainModelMlTrainResultResponseErr
import api.kotlinproject.common.trainmodel.TrainModelMlTrainResultResponseErrWithData
import api.kotlinproject.common.trainmodel.TrainModelMlTrainResultResponseOk
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.trainModelTrainResult(title: String) = worker {
    this.title = title
    description = "Тренировка модели перед сохранением в БД"
    on { state == MdlState.RUNNING }
    handle {
        when(val result = mlTrainModelTrainResult.usingmodelMl(rq = TrainModelMlTrainResultRequest(mlValidatedAnalytic.deepCopy()))) {
            is TrainModelMlTrainResultResponseOk -> mlTrainModelTrainResultDone = result.data
            is TrainModelMlTrainResultResponseErr -> fail(result.errors)
            is TrainModelMlTrainResultResponseErrWithData -> {
                fail(result.errors)
                mlTrainModelTrainResultDone = result.data
            }
        }
    }
}
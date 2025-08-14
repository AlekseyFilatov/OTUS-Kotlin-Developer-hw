package api.kotlinproject.biz.trainmodel


import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.trainmodel.TrainModelMlTransformRequest
import api.kotlinproject.common.trainmodel.TrainModelMlTransformResponseErr
import api.kotlinproject.common.trainmodel.TrainModelMlTransformResponseErrWithData
import api.kotlinproject.common.trainmodel.TrainModelMlTransformResponseOk
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.trainModelTransform(title: String) = worker {
    this.title = title
    description = "Тренировка модели перед сохранением в БД"
    on { state == MdlState.RUNNING }
    handle {
        when(val result = mlTrainModelTransform.usingmodelMl(rq = TrainModelMlTransformRequest(mlValidatedTransform.deepCopy()))) {
            is TrainModelMlTransformResponseOk -> mlTrainModelTransformDone = result.data
            is TrainModelMlTransformResponseErr -> fail(result.errors)
            is TrainModelMlTransformResponseErrWithData -> {
                fail(result.errors)
                mlTrainModelTransformDone = result.data
            }
        }
    }
}
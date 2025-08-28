package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.common.trainmodel.ITrainModelMlTrainResult
import api.kotlinproject.common.trainmodel.ITrainModelMlTransform
import api.kotlinproject.common.trainmodel.MlTrainModelType
import io.ktor.server.application.*

actual fun Application.getTrainModel(type: MlTrainModelType): ITrainModelMl {
    return ITrainModelMl.NONE
}

actual fun Application.getTrainModelTransform(type: MlTrainModelType): ITrainModelMlTransform {
    return ITrainModelMlTransform.NONE
}

actual fun Application.getTrainModelTrainResult(type: MlTrainModelType): ITrainModelMlTrainResult {
    return ITrainModelMlTrainResult.NONE
}
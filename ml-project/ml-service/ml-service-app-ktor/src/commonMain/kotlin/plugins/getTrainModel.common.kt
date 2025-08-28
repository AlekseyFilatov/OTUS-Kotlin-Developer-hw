package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.common.trainmodel.ITrainModelMlTrainResult
import api.kotlinproject.common.trainmodel.ITrainModelMlTransform
import api.kotlinproject.common.trainmodel.MlTrainModelType
import io.ktor.server.application.*

expect fun Application.getTrainModel(type: MlTrainModelType): ITrainModelMl

expect fun Application.getTrainModelTransform(type: MlTrainModelType): ITrainModelMlTransform

expect fun Application.getTrainModelTrainResult(type: MlTrainModelType): ITrainModelMlTrainResult
package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.common.trainmodel.MlTrainModelType
import io.ktor.server.application.*


expect fun Application.getTrainModel(type: MlTrainModelType): ITrainModelMl
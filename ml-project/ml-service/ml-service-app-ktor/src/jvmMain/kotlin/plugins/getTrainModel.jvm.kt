package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.backend.repository.inmemory.MlTrainModelStub
import api.kotlinproject.backend.repository.inmemory.MlTrainModelTrainResultStub
import api.kotlinproject.backend.repository.inmemory.MlTrainModelTransformStub
import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.common.trainmodel.ITrainModelMlTrainResult
import api.kotlinproject.common.trainmodel.ITrainModelMlTransform
import api.kotlinproject.common.trainmodel.MlTrainModelType
import api.kotlinproject.mlmodel.mlxgboost.XGBoostDataFrame
import api.kotlinproject.mlmodel.mlxgboost.XGBoostDataFrameTrainResult
import api.kotlinproject.mlmodel.mlxgboost.XGBoostDataFrameTransform
import io.ktor.server.application.*

actual fun Application.getTrainModel(type: MlTrainModelType): ITrainModelMl {
    return when(type) {
        MlTrainModelType.XGBoost -> initXGBoost()
        MlTrainModelType.FOREST -> initXGBoost()
        MlTrainModelType.RAPIDS -> initXGBoost()
        MlTrainModelType.STUB -> initStub()
    }
}

private fun Application.initXGBoost(): ITrainModelMl {
    return XGBoostDataFrame()
}

private fun Application.initStub(): ITrainModelMl {
    return MlTrainModelStub()
}

actual fun Application.getTrainModelTransform(type: MlTrainModelType): ITrainModelMlTransform {
    return when(type) {
        MlTrainModelType.XGBoost -> initXGBoostTransform()
        MlTrainModelType.FOREST -> initXGBoostTransform()
        MlTrainModelType.RAPIDS -> initXGBoostTransform()
        MlTrainModelType.STUB -> initStubTransform()
    }
}

private fun Application.initXGBoostTransform(): ITrainModelMlTransform {
    return XGBoostDataFrameTransform()
}

private fun Application.initStubTransform(): ITrainModelMlTransform {
    return MlTrainModelTransformStub()
}

actual fun Application.getTrainModelTrainResult(type: MlTrainModelType): ITrainModelMlTrainResult {
    return when(type) {
        MlTrainModelType.XGBoost -> initXGBoostTrainResult()
        MlTrainModelType.FOREST -> initXGBoostTrainResult()
        MlTrainModelType.RAPIDS -> initXGBoostTrainResult()
        MlTrainModelType.STUB -> TODO()
    }
}

private fun Application.initXGBoostTrainResult(): ITrainModelMlTrainResult {
    return XGBoostDataFrameTrainResult()
}

private fun Application.initStubTrainResult(): ITrainModelMlTrainResult {
    return MlTrainModelTrainResultStub()
}
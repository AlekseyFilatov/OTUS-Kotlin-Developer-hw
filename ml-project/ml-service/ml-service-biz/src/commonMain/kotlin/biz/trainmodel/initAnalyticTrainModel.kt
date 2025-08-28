package api.kotlinproject.biz.trainmodel


import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorSystem
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.common.trainmodel.ITrainModelMlTrainResult
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker
import biz.exceptions.MdlMlTrainModelNotConfiguredException

fun ICorChainDsl<MdlContext>.initAnalyticTrainModel(title: String) = worker {
    this.title = title
    description = """
        Вычисление используемой модели в зависимости от запрошенной       
    """.trimIndent()

    handle {
        mlTrainModelTrainResult = when {
            workMode == MdlWorkMode.STUB -> corSettingsTrainResult.modelStubTrainResult
            mlAnalyticMl.title.asString() == "xgboost" && workMode != MdlWorkMode.STUB -> corSettingsTrainResult.modelXGBoostTrainResult
            mlAnalyticMl.title.asString() == "forrest" && workMode != MdlWorkMode.STUB -> corSettingsTrainResult.modelForestTrainResult
            mlAnalyticMl.title.asString() == "rapids" && workMode != MdlWorkMode.STUB -> corSettingsTrainResult.modelRapidsTrainResult
            mlAnalyticMl.title.asString() == "stub" -> corSettingsTrainResult.modelStubTrainResult
            else -> ITrainModelMlTrainResult.NONE
        }
        if (mlTrainModelTrainResult == ITrainModelMlTrainResult.NONE && workMode != MdlWorkMode.STUB && workMode != MdlWorkMode.TEST) fail(
            errorSystem(
                violationCode = "dbNotConfiguredAnalyticModel",
                e = MdlMlTrainModelNotConfiguredException(titleMLModel)
            )
        )
    }
}

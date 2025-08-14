package biz.trainmodel

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorSystem
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker
import biz.exceptions.MdlMlTrainModelNotConfiguredException

fun ICorChainDsl<MdlContext>.initTrainModelTrainResult(title: String) = worker {
    this.title = title
    description = """
        Вычисление используемой модели в зависимости от запрошенной       
    """.trimIndent()

    handle {
        mlTrainModelTrainResult = corSettingsTrainResult.modelStubTrainResult
        /* mlTrainModelTrainResult = when {
             mlRequest.title == "xgboost" -> corSettings.modelXGBoost
             mlRequest.title == "forrest" -> corSettings.modelXGBoost
             mlRequest.title == "rapids" -> corSettings.modelXGBoost
             else -> ITrainModelMl.NONE
         }*/
        if (mlTrainModelTrainResult == ITrainModelMl.NONE && workMode != MdlWorkMode.STUB  && workMode != MdlWorkMode.TEST) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = MdlMlTrainModelNotConfiguredException(titleMLModel)
            )
        )
    }
}

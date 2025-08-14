package api.kotlinproject.biz.trainmodel


import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorSystem
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker
import biz.exceptions.MdlMlTrainModelNotConfiguredException

fun ICorChainDsl<MdlContext>.initTrainModel(title: String) = worker {
    this.title = title
    description = """
        Вычисление используемой модели в зависимости от запрошенной       
    """.trimIndent()

    handle {
        mlTrainModel = when {
            workMode == MdlWorkMode.STUB -> corSettings.modelStub
            mlRequest.title == "xgboost" && workMode != MdlWorkMode.STUB -> corSettings.modelXGBoost
            mlRequest.title == "forrest" && workMode != MdlWorkMode.STUB -> corSettings.modelForest
            mlRequest.title == "rapids" && workMode != MdlWorkMode.STUB -> corSettings.modelRapids
            mlRequest.title == "stub" && workMode != MdlWorkMode.STUB -> corSettings.modelStub
            else -> ITrainModelMl.NONE
        }
        if (mlTrainModel == ITrainModelMl.NONE && workMode != MdlWorkMode.STUB && workMode != MdlWorkMode.TEST) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = MdlMlTrainModelNotConfiguredException(titleMLModel)
            )
        )
    }
}

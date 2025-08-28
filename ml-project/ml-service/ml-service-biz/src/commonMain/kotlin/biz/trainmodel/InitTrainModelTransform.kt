package biz.trainmodel

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorSystem
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.common.trainmodel.ITrainModelMlTransform
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker
import biz.exceptions.MdlMlTrainModelNotConfiguredException

fun ICorChainDsl<MdlContext>.initTrainModelTransform(title: String) = worker {
    this.title = title
    description = """
        Вычисление используемой модели в зависимости от запрошенной       
    """.trimIndent()

    handle {
        mlTrainModelTransform = when {
            workMode == MdlWorkMode.STUB -> corSettingsTransform.modelStubTransform
            mlTransformMl.title.asString() == "xgboost" && workMode != MdlWorkMode.STUB -> corSettingsTransform.modelXGBoostTransform
            mlTransformMl.title.asString() == "forrest" && workMode != MdlWorkMode.STUB -> corSettingsTransform.modelForestTransform
            mlTransformMl.title.asString() == "rapids" && workMode != MdlWorkMode.STUB -> corSettingsTransform.modelRapidsTransform
            mlTransformMl.title.asString() == "stub" -> corSettingsTransform.modelStubTransform
            else -> ITrainModelMlTransform.NONE
        }
        if (mlTrainModelTransform == ITrainModelMlTransform.NONE && workMode != MdlWorkMode.STUB  && workMode != MdlWorkMode.TEST) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = MdlMlTrainModelNotConfiguredException(titleMLModel)
            )
        )
    }
}

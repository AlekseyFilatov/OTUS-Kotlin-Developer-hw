package biz.validation

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorValidation
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker


fun ICorChainDsl<MdlContext>.validateAnalyticHasContent(title: String) = worker {
    this.title = title
    this.description = """
        Проверяем, что все поля в запросе заполнены
    """.trimIndent()
    val regExp = Regex("\\p{L}")
    on {
        mlTrainResultValidating.id.asString().isEmpty() ||
                mlTrainResultValidating.dateTime.isEmpty() ||
                mlTrainResultValidating.labelDatetime.isEmpty()
        /*mlAnalyticValidating.ticker.asString().isEmpty() ||
                !mlAnalyticValidating.ticker.asString().contains(regExp) ||
                mlAnalyticValidating.taskNumber.asString().isEmpty() ||
                mlAnalyticValidating.dateStart?.isEmpty() == true ||
                mlAnalyticValidating.dateEnd?.isEmpty() == true ||
                mlAnalyticValidating.modelParameters.treeMethod.isEmpty() ||
                mlAnalyticValidating.modelParameters.processType.isEmpty() ||
                mlAnalyticValidating.modelParameters.updater.isEmpty()*/
    }
    handle {
        fail(
            errorValidation(
                field = "analytic fields",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}
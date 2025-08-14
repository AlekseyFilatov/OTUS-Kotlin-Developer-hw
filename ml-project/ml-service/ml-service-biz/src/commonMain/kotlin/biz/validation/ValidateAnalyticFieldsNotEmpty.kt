package biz.validation

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorValidation
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.validateAnalyticFieldsNotEmpty(title: String) = worker {
    this.title = title
    on {
        mlAnalyticValidating.id.asString().isEmpty() ||
                mlAnalyticValidating.ticker.asString().isEmpty() ||
                mlAnalyticValidating.taskNumber.asString().isEmpty()
        /*mlAnalyticValidating.ticker.asString().isEmpty() ||
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
                violationCode = "empty",
                description = "analytic fields must not be empty"
            )
        )
    }
}
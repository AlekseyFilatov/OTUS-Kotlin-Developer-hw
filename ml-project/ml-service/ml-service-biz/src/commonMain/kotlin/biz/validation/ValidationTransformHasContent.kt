package biz.validation

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorValidation
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker


fun ICorChainDsl<MdlContext>.validateTransformHasContent(title: String) = worker {
    this.title = title
    this.description = """
        Проверяем, что все поля в запросе заполнены
    """.trimIndent()
    val regExp = Regex("\\p{L}")
    on { mlTransformValidating.ticker?.isEmpty() == true || !mlTransformValidating.ticker?.contains(regExp)!!
            || mlTransformValidating.taskNumber?.isEmpty() == true
            || mlTransformValidating.dateStart?.isEmpty() == true
            || mlTransformValidating.dateEnd?.isEmpty() == true
    }
    handle {
        fail(
            errorValidation(
                field = "transform fields",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}
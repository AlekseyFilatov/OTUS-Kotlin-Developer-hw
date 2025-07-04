package api.kotlinproject.biz.validation

import api.kotlinproject.common.helpers.errorValidation
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

// пример обработки ошибки в рамках бизнес-цепочки
fun ICorChainDsl<MdlContext>.validateDescriptionHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { mlValidating.description.isNotEmpty() && !mlValidating.description.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "description",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}

package api.kotlinproject.biz.validation

import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker
import api.kotlinproject.common.helpers.errorValidation
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail

fun ICorChainDsl<MdlContext>.validateDescriptionNotEmpty(title: String) = worker {
    this.title = title
    on { mlValidating.description.isEmpty() }
    handle {
        fail(
            errorValidation(
            field = "description",
            violationCode = "empty",
            description = "field must not be empty"
        )
        )
    }
}

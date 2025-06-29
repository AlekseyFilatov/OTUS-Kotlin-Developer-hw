package api.kotlinproject.biz.validation

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorValidation
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

// смотрим пример COR DSL валидации
fun ICorChainDsl<MdlContext>.validateTitleNotEmpty(title: String) = worker {
    this.title = title
    on { mlValidating.title?.isEmpty() == true  }
    handle {
        fail(
            errorValidation(
                field = "title",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

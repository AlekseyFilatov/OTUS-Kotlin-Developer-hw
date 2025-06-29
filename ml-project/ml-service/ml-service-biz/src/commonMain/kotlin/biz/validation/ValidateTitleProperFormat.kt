package api.kotlinproject.biz.validation

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorValidation
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.validateTitleProperFormat(title: String) = worker {
    this.title = title


    val regExp = Regex("^[0-9a-zA-Z#:-]+$")
    on { mlValidating.title != "" && !mlValidating.title.toString().matches(regExp) }
    handle {
        val encodedId = mlValidating.title.toString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "title",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}

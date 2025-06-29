package api.kotlinproject.biz.validation

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.errorValidation
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.validateTitleHasContent(title: String) = worker {
    this.title = title
    this.description = """
        Проверяем, что у нас есть какие-то слова в заголовке.
        Отказываем в публикации заголовков, в которых только бессмысленные символы типа %^&^$^%#^))&^*&%^^&
    """.trimIndent()
    val regExp = Regex("\\p{L}")
    on { mlValidating.title.toString().isNotEmpty() && !mlValidating.title?.contains(regExp)!! }
    handle {
        fail(
            errorValidation(
            field = "title",
            violationCode = "noContent",
            description = "field must contain letters"
        )
        )
    }
}

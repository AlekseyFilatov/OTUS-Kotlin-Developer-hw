package api.kotlinproject.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MkplMlTitle(private val title: String) {
    fun asString() = title

    //fun asTitle() = Title.decode(title)

    companion object {
        val NONE = MkplMlTitle("")
    }
}

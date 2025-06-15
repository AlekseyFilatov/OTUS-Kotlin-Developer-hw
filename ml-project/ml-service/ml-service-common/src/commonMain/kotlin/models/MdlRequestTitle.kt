package api.kotlinproject.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MdlRequestTitle(private val title: String) {
    fun asString() = title

    companion object {
        val NONE = MdlRequestTitle("")
    }
}

package api.kotlinproject.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class MdlUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MdlUserId("")
    }
}

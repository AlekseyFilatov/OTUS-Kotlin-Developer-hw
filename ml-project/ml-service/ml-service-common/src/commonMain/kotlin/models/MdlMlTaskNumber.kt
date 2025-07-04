package api.kotlinproject.common.models
import kotlin.jvm.JvmInline

@JvmInline
value class MdlMlTaskNumber(private val taskNumber: String) {
    fun asString() = taskNumber

    companion object {
        val NONE = MdlMlTaskNumber("")
    }
}
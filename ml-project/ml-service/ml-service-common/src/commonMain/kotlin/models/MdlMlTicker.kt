package api.kotlinproject.common.models
import kotlin.jvm.JvmInline

@JvmInline
value class MdlMlTicker(private val ticker: String) {
    fun asString() = ticker

    companion object {
        val NONE = MdlMlTicker("")
    }
}
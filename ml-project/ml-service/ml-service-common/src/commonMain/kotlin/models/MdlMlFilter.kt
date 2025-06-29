package api.kotlinproject.common.models

data class MdlMlFilter(
    var searchString: String = "",
) {
    fun deepCopy(): MdlMlFilter = copy()

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = MdlMlFilter()
    }
}

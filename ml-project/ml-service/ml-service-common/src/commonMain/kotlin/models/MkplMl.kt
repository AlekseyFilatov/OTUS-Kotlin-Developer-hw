package api.kotlinproject.common.models

data class MkplMl(
    var title: String? = "",
    var description: String = "",
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = MkplMl()
    }

}

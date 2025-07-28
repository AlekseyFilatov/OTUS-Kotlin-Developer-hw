package api.kotlinproject.common.models

data class MdlMl(
    var title: String? = "",
    var description: String = "",
    var id :MdlMlId = MdlMlId.NONE
)
{
    fun deepCopy(): MdlMl = copy(
    )

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = MdlMl()
    }

}

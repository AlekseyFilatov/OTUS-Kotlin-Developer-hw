package api.kotlinproject.homeworkdsl.models

data class Column(
    val name: String,
    val alias: String? = null
) {
    override fun toString(): String {
           return if(alias.isNullOrEmpty()) name else "$name as name"
    }
}
package api.kotlinproject.homeworkdsl.models

data class Select(
    val from: String,
    val columns: List<Column>,
    val whereBlocks: List<Where>
) {
    override fun toString(): String {
        "*".let {  col ->
            if(columns.isEmpty()) col else columns.joinToString(", ") { it.toString() }
                }.let {  col ->
                    val whereStr = if (whereBlocks.isEmpty()) "" else " where " + whereBlocks.joinToString(separator = " and ") { it.toString() }
                    return "select $col from $from".plus(whereStr)
                }
    }
}
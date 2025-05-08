package api.kotlinproject.homeworkdsl.models

data class Condition(
    val columnName: String = "",
    val operator: Operator = Operator.NONE,
    val value: String? = null
) {
    override fun toString(): String {
        value.let { valueCond ->
            return if (valueCond.isNullOrEmpty()) {
                when (operator) {
                    Operator.EQ -> "is"
                    Operator.NON_EQ -> "!is"
                    else -> throw Exception("Operator not specified")
                }.let {
                    "$columnName $it null"
                }
            } else {
                when (operator) {
                    Operator.EQ -> "="
                    Operator.NON_EQ -> "!="
                    else -> throw Exception("Operator not specified")
                }.let {
                    "$columnName $it $value"
                }
            }
        }
    }
}
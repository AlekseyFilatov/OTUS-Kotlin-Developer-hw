package api.kotlinproject.homeworkdsl.models
data class Where (
    val logic: ConditionsLogic = ConditionsLogic.AND,
    val conditions: List<Condition> = emptyList()
) {
    override fun toString(): String {
       when (logic) {
            ConditionsLogic.AND -> "and"
            ConditionsLogic.OR -> "or"
        }.let { logicCond ->
           conditions.joinToString(" $logicCond ") { rec -> rec.toString() }
            .let { conditionsStr ->
                return if (conditions.count() > 1) "($conditionsStr)"
                else conditionsStr
            }
        }
    }
}
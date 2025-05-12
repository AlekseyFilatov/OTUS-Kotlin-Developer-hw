package api.kotlinproject.homeworkdsl

import api.kotlinproject.homeworkdsl.models.Condition
import api.kotlinproject.homeworkdsl.models.ConditionsLogic
import api.kotlinproject.homeworkdsl.models.Operator
import api.kotlinproject.homeworkdsl.models.Where

@DslMarker
annotation class QueryWhereDsl
@QueryWhereDsl
class SqlWhereBuilder(val logic: ConditionsLogic = ConditionsLogic.AND) {
    private var conditions: MutableList<Condition> = mutableListOf()

    fun hasConditions(): Boolean = conditions.isNotEmpty()

    infix fun String.eq(value: String?) {
        value.let { valStr ->
            if(valStr.isNullOrEmpty()) valStr else "'$valStr'"
                }.let { valStr ->
                    conditions.add(Condition(this, Operator.EQ, valStr))
                }
    }

    infix fun String.eq(value: Number) {
        conditions.add(Condition(this, Operator.EQ, value.toString()))
    }

    infix fun String.nonEq(value: String?) {
        value.let { valStr ->
            if(valStr.isNullOrEmpty()) valStr else "'$valStr'"
                }.let { valStr ->
                   conditions.add(Condition(this, Operator.NON_EQ, valStr))
                }
    }

    infix fun String.nonEq(value: Number) {
        conditions.add(Condition(this, Operator.NON_EQ, value.toString()))
    }
    fun build(): Where {
        return Where(
            logic,
            conditions
        )
    }
}
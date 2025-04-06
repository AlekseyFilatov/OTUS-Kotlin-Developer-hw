package api.kotlinproject.homeworkdsl

import api.kotlinproject.homeworkdsl.models.ConditionsLogic
import api.kotlinproject.homeworkdsl.models.Where
import api.kotlinproject.homeworkdsl.models.Column
import api.kotlinproject.homeworkdsl.models.Select

@DslMarker
annotation class QueryDsl

@DslMarker
annotation class QueryFromDsl

@DslMarker
annotation class QuerySelectDsl

@QueryDsl
fun query(block: SqlSelectBuilder.() -> Unit) = SqlSelectBuilder().apply(block)

@QueryDsl
class SqlSelectBuilder {
    private var tableName: String = ""
    private var columns: MutableList<Column> = mutableListOf()
    private var whereBlockBuilders: MutableList<SqlWhereBuilder> = mutableListOf()

    @QueryFromDsl
    fun from(table: String) {
        tableName = table
    }


    @QuerySelectDsl
    fun select(vararg columnNames: String) {
        columnNames.forEach { column -> columns.add(Column(column)) }
    }

    @QueryWhereDsl
    fun where(block: SqlWhereBuilder.() -> Unit) {
        SqlWhereBuilder().let{
            whereBlockBuilders.add(it)
            block(it)
        }
    }

    @QueryWhereDsl
    fun or(block: SqlWhereBuilder.() -> Unit) {
        SqlWhereBuilder(ConditionsLogic.OR).let{
            whereBlockBuilders.add(it)
            block(it)
        }
    }

    fun build(): Select {
        with(mutableListOf<Where>()) {
            tableName
                .takeIf { tableName.isEmpty() }?.let { throw Exception("Не установлена таблица") }
            whereBlockBuilders.forEach { it.takeIf { it.hasConditions() }?.let { this.add(it.build()) } }
            return Select(tableName, columns, this)
        }
    }
}
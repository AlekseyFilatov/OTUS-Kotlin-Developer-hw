package api.kotlinproject.common.models


data class MdlMlTrainResult (
    /* Дата/Время */
    var dateTime: kotlin.String = ""
    ,

    /* Результат */
    var close: kotlin.Double = 0.0
    ,

    /* Дата/Время */
    var labelDatetime: kotlin.String = ""
    ,

    /* Реальный результат */
    var realResult: kotlin.Double = 0.0
    ,

    /* Предикат */
    var prediction: kotlin.Double = 0.0
    ,

    /* Ошибка */
    var error: kotlin.Double = 0.0
){
    fun isEmpty() = this == NONE

    fun deepCopy(): MdlMlTrainResult = copy(
    )

    companion object {
        private val NONE = MdlMlTrainResult()
    }

}
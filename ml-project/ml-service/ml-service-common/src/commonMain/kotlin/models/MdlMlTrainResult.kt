package api.kotlinproject.common.models


data class MdlMlTrainResult (
    /* Дата/Время */
    var dateTime: kotlin.String? = null
    ,

    /* Результат */
    var close: kotlin.Double? = null
    ,

    /* Дата/Время */
    var labelDatetime: kotlin.String? = null
    ,

    /* Реальный результат */
    var realResult: kotlin.Double? = null
    ,

    /* Предикат */
    var prediction: kotlin.Double? = null
    ,

    /* Ошибка */
    var error: kotlin.Double? = null
){
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = MdlMlTrainResult()
    }

}
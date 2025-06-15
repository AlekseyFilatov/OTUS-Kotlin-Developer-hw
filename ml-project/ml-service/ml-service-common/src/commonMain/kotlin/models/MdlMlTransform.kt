package api.kotlinproject.common.models

data class MdlMlTransform  constructor(
    /* ticker */
    var ticker: kotlin.String? = null
    ,

    /* task_number */
    var taskNumber: kotlin.String? = null
    ,

    /* Дата/Время */
    var dateStart: kotlin.String? = null
    ,

    /* Дата/Время */
    var dateEnd: kotlin.String? = null
    ,

    /* date_offset */
    var dateOffset: kotlin.Long? = null
    ,

    /* batch_size */
    var batchSize: kotlin.Int? = null
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = MdlMlTransform()
    }

}
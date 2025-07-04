package api.kotlinproject.common.models

data class MdlMlTransform  constructor(
    /* ticker */
    var ticker: kotlin.String? = ""
    ,

    /* task_number */
    var taskNumber: kotlin.String? = ""
    ,

    /* Дата/Время */
    var dateStart: kotlin.String? = ""
    ,

    /* Дата/Время */
    var dateEnd: kotlin.String? = ""
    ,

    /* date_offset */
    var dateOffset: kotlin.Long? = 0
    ,

    /* batch_size */
    var batchSize: kotlin.Int? = 0
) {
    fun isEmpty() = this == NONE

    fun deepCopy(): MdlMlTransform = copy(
    )

    companion object {
        private val NONE = MdlMlTransform()
    }

}
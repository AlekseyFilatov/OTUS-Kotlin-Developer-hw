package api.kotlinproject.common.models

import kotlin.uuid.ExperimentalUuidApi


data class MdlMlAnalytic @OptIn(ExperimentalUuidApi::class) constructor(
    /* ticker */
    var ticker: kotlin.String? = null,

    /* task_number */
    var taskNumber: kotlin.String? = null,

    /* Дата/Время */
    var dateStart: kotlin.String? = null,

    /* Дата/Время */
    var dateEnd: kotlin.String? = null,

    /* eval_pivot_point */
    var evalPivotPoint: kotlin.Long? = null,

    var modelParameters: MdlMlModelParameters? = null,

    /* date_offset */
    var dateOffset: kotlin.Long? = null,

    /* batch_size */
    var batchSize: kotlin.Long? = null
) {
    fun isEmpty() = this == NONE

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        private val NONE = MdlMlAnalytic()
    }

}
package api.kotlinproject.common.models


data class MdlMlAnalytic constructor(

    /* ticker */
    var ticker: MdlMlTicker = MdlMlTicker.NONE,

    /* task_number */
    var taskNumber: MdlMlTaskNumber = MdlMlTaskNumber.NONE,

    /* Дата/Время */
    var dateStart: kotlin.String? = "",

    /* Дата/Время */
    var dateEnd: kotlin.String? = "",

    /* eval_pivot_point */
    var evalPivotPoint: kotlin.Long? = 0,

    var modelParameters: MdlMlModelParameters = MdlMlModelParameters(),

    /* date_offset */
    var dateOffset: kotlin.Long? = 0,

    /* batch_size */
    var batchSize: kotlin.Long? = 0,

    var id :MdlMlId = MdlMlId.NONE
) {
    fun isEmpty() = this == NONE

    fun deepCopy(): MdlMlAnalytic = copy(
    )

    companion object {
        private val NONE = MdlMlAnalytic()
    }

}
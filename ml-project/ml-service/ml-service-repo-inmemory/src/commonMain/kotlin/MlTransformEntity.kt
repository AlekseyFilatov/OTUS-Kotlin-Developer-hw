package api.kotlinproject.repo.inmemory

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTitle
import api.kotlinproject.common.models.MdlMlTransform

data class MlTransformEntity(
    val id: String? = null,
    val ticker: String? = null,
    val taskNumber: String? = null,
    val dateStart: String? = null,
    val dateEnd: String? = null,
    val dateOffset: String? = null,
    val batchSize: String? = null,
    val title: String? = null,
) {
    constructor(model: MdlMlTransform): this(
        ticker = model.ticker,
        taskNumber = model.taskNumber,
        dateStart = model.dateStart,
        dateEnd = model.dateEnd,
        dateOffset = model.dateOffset.toString(),
        batchSize = model.batchSize.toString(),
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.asString()
    )

    fun toInternal() = MdlMlTransform(
        id = id?.let { MdlMlId(it) } ?: MdlMlId.NONE,
        ticker = ticker,
        taskNumber = taskNumber,
        dateStart = dateStart,
        dateEnd = dateEnd,
        dateOffset = dateOffset?.toLong(),
        batchSize = batchSize?.toInt(),
        title = title?.let { MdlMlTitle(it) } ?: MdlMlTitle.NONE
        // ownerId = ownerId?.let { MdlUserId(it) }?: MdlUserId.NONE,
    )
}

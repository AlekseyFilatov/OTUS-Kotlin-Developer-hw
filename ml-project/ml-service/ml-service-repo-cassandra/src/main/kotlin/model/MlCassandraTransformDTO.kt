package api.kotlinproject.backend.repo.cassandra.model

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTitle
import api.kotlinproject.common.models.MdlMlTransform
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey

@Entity
data class MlCassandraTransformDTO(
    @field:CqlName(COLUMN_ID)
    @field:PartitionKey // можно задать порядок
    var id: String? = null,
    @field:CqlName(COLUMN_TICKER)
    var ticker: String? = null,
    @field:CqlName(COLUMN_TASKNUMBER)
    var taskNumber: String? = null,
    @field:CqlName(COLUMN_DATESTART)
    var dateStart: String? = null,
    @field:CqlName(COLUMN_DATEEND)
    var dateEnd: String? = null,
    @field:CqlName(COLUMN_OFFSET)
    var dateOffset: String? = null,
    @field:CqlName(COLUMN_BATCHSIZE)
    var batchSize: String? = null,
    @field:CqlName(COLUMN_TITLE)
    var title: String? = null
) {
    constructor(model: MdlMlTransform) : this(
        ticker = model.ticker,
        taskNumber = model.taskNumber,
        dateStart = model.dateStart,
        dateEnd = model.dateEnd,
        dateOffset = model.dateOffset.toString(),
        batchSize = model.batchSize.toString(),
        id = model.id.asString(),
        title = model.title.asString()
    )

    fun toMlTransformModel(): MdlMlTransform =
        MdlMlTransform(
            id = id?.let { MdlMlId(it) } ?: MdlMlId.NONE,
            ticker = ticker,
            taskNumber = taskNumber,
            dateStart = dateStart,
            dateEnd = dateEnd,
            dateOffset = dateOffset?.toLong(),
            batchSize = batchSize?.toInt(),
            title = title?.let { MdlMlTitle(it) } ?: MdlMlTitle.NONE,
        )

    companion object {
        const val TABLE_NAME = "transform_mls"

        const val COLUMN_TICKER = "ticker"
        const val COLUMN_TASKNUMBER = "tasknumber"
        const val COLUMN_DATESTART = "datestart"
        const val COLUMN_DATEEND = "dateend"
        const val COLUMN_OFFSET = "dateoffset"
        const val COLUMN_BATCHSIZE = "batchsize"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"

    }
}



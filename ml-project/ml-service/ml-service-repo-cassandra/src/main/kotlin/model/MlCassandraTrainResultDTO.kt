package api.kotlinproject.backend.repo.cassandra.model

import api.kotlinproject.common.models.MdlMlId
import api.kotlinproject.common.models.MdlMlTrainResult
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey


@Entity
data class MlCassandraTrainResultDTO(
    @field:CqlName(COLUMN_DATETIME)
    var dateTime: String? = null,
    @field:CqlName(COLUMN_CLOSE)
    var close: String? = null,
    @field:CqlName(COLUMN_LABELDATETIME)
    var labelDatetime: String? = null,
    @field:CqlName(COLUMN_REALRESULT)
    var realResult: String? = null,
    @field:CqlName(COLUMN_PREDICTION)
    var prediction: String? = null,
    @field:CqlName(COLUMN_ERROR)
    var error: String? = null,
    @field:CqlName(COLUMN_ID)
    @field:PartitionKey // можно задать порядок
    var id: String? = null
) {
    constructor(model: MdlMlTrainResult) : this(
        dateTime = model.dateTime,
        close = model.close.toString(),
        labelDatetime = model.labelDatetime,
        realResult = model.realResult.toString(),
        prediction = model.prediction.toString(),
        error = model.error.toString(),
        id = model.id.asString().takeIf { it.isNotBlank() },
    )

    fun  toMlTrainResultModel() = MdlMlTrainResult(
        id = id?.let { MdlMlId(it) }?: MdlMlId.NONE,
        dateTime = dateTime.toString(),
        close = close?.toDouble() ?: 0.0,
        labelDatetime = labelDatetime.toString(),
        realResult = realResult?.toDouble() ?: 0.0,
        prediction = prediction?.toDouble() ?: 0.0,
        error = error?.toDouble() ?: 0.0,
    )

    companion object {
        const val TABLE_NAME = "trainresult_mls"

        const val COLUMN_DATETIME = "dateTime"
        const val COLUMN_CLOSE = "close"
        const val COLUMN_LABELDATETIME = "labelDatetime"
        const val COLUMN_REALRESULT = "realResult"
        const val COLUMN_PREDICTION = "prediction"
        const val COLUMN_ERROR = "error"
        const val COLUMN_ID = "id"

    }
}

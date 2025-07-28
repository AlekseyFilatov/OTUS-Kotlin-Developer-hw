package api.kotlinproject.backend.repo.cassandra.model

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey

@Entity
data class MlCassandraDTO(
    @field:CqlName(COLUMN_ID)
    @field:PartitionKey // можно задать порядок
    var id: String? = null,
    @field:CqlName(COLUMN_TITLE)
    var title: String? = null,
    @field:CqlName(COLUMN_DESCRIPTION)
    var description: String? = null,

) {
    constructor(mlModel: MdlMl) : this(
        id = mlModel.id.takeIf { it != MdlMlId.NONE }?.asString(),
        title = mlModel.title,
        description = mlModel.description.takeIf { it.isNotBlank() },
    )

    fun toMlModel(): MdlMl =
        MdlMl(
            id = id?.let { MdlMlId(it) } ?: MdlMlId.NONE,
            title = title ?: "",
            description = description ?: "",
        )

    companion object {
        const val TABLE_NAME = "mlservice_mls"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"

    }
}

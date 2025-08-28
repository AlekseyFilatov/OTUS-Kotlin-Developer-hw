package api.kotlinproject.repo.inmemory

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlId

data class MlEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    //val ownerId: String? = null,
) {
    constructor(model: MdlMl): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.toString().isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
       // ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
    )

    fun toInternal() = MdlMl(
        id = id?.let { MdlMlId(it) }?: MdlMlId.NONE,
        title = title?: "",
        description = description?: "",
        //ownerId = ownerId?.let { MdlUserId(it) }?: MdlUserId.NONE,
    )
}

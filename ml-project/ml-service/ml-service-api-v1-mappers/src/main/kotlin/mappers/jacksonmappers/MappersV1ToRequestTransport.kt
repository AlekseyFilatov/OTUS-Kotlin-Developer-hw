package api.kotlinproject.mappers.v1

import api.kotlinproject.api.jackson.v1.models.MlCreateObject
import api.kotlinproject.api.jackson.v1.models.MlDeleteObject
import api.kotlinproject.api.jackson.v1.models.MlReadObject
import api.kotlinproject.api.jackson.v1.models.MlUpdateObject
import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlTitle

fun MdlMl.toTransportCreate() = MlCreateObject(
    title = title.takeIf { it.toString().isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    id = id.asString()
)

fun MdlMl.toTransportRead() = MlReadObject(
    title = title.takeIf { it != MdlMlTitle.NONE.asString() },
    id = id.asString()
)

fun MdlMl.toTransportUpdate() = MlUpdateObject(
    title = title.takeIf { it.toString().isNotBlank()  },
    id = id.asString()
)

fun MdlMl.toTransportDelete() = MlDeleteObject(
    title = title.takeIf { it != MdlMlTitle.NONE.asString() },
    id = id.asString()
)

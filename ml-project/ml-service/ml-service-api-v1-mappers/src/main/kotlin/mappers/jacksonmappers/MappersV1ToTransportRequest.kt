package api.kotlinproject.mappers.v1.jacksonmappers

import api.kotlinproject.api.jackson.v1.models.MlCreateObject
import api.kotlinproject.api.jackson.v1.models.MlDeleteObject
import api.kotlinproject.api.jackson.v1.models.MlReadObject
import api.kotlinproject.api.jackson.v1.models.MlUpdateObject
import api.kotlinproject.common.models.MdlMl


fun MdlMl.toTransportCreateMl() = MlCreateObject(
    title = title,
    description = description
    )

fun MdlMl.toTransportReadMl() = MlReadObject(
    title = title
)

fun MdlMl.toTransportUpdateMl() = MlUpdateObject(
    title = title
)

fun MdlMl.toTransportDeleteMl() = MlDeleteObject(
    title = title
)
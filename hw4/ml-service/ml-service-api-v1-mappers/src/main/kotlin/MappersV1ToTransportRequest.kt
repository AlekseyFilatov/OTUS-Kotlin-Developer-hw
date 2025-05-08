package api.kotlinproject.mappers.v1

import api.kotlinproject.api.v1.models.MlCreateObject
import api.kotlinproject.api.v1.models.MlDeleteObject
import api.kotlinproject.api.v1.models.MlReadObject
import api.kotlinproject.api.v1.models.MlUpdateObject
import api.kotlinproject.common.models.MkplMl
import api.kotlinproject.common.models.MkplMlTitle


fun MkplMl.toTransportCreateMl() = MlCreateObject(
    title = title,
    description = description
    )

fun MkplMl.toTransportReadMl() = MlReadObject(
    title = title
)

fun MkplMl.toTransportUpdateMl() = MlUpdateObject(
    title = title
)

fun MkplMl.toTransportDeleteMl() = MlDeleteObject(
    title = title
)
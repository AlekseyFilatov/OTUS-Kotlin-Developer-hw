package api.kotlinproject.common

import kotlinx.datetime.Instant
import api.kotlinproject.common.models.*
import api.kotlinproject.common.stubs.MkplStubs

data class MkplContext(
    var command: MkplCommand = MkplCommand.NONE,
    var state: MkplState = MkplState.NONE,
    val errors: MutableList<MkplError> = mutableListOf(),

    var workMode: MkplWorkMode = MkplWorkMode.PROD,
    var stubCase: MkplStubs = MkplStubs.NONE,

    var requestId: MkplRequestId = MkplRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var mlRequest: MkplMl = MkplMl(),
    var mlFilterRequest: MkplMlFilter = MkplMlFilter(),

    var mlResponse: MkplMl = MkplMl(),
    var mlsResponse: MutableList<MkplMl> = mutableListOf(),

    )

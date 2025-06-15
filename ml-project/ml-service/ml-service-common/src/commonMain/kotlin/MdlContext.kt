package api.kotlinproject.common

import api.kotlinproject.common.models.*
import api.kotlinproject.common.stubs.MdlStubs
import api.kotlinproject.common.ws.IMdlWsSession
import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi

data class MdlContext @OptIn(ExperimentalUuidApi::class) constructor(
    var command: MdlCommand = MdlCommand.NONE,
    var state: MdlState = MdlState.NONE,
    val errors: MutableList<MdlError> = mutableListOf(),
    var wsSession: IMdlWsSession = IMdlWsSession.NONE,

    var workMode: MdlWorkMode = MdlWorkMode.PROD,
    var stubCase: MdlStubs = MdlStubs.NONE,

    var requestTitle: MdlRequestTitle = MdlRequestTitle.NONE,
    var timeStart: Instant = Instant.NONE,
    var mlRequest: MdlMl = MdlMl(),
    var mlFilterRequest: MdlMlFilter = MdlMlFilter(),
    var mlAnalyticMl: MdlMlAnalytic = MdlMlAnalytic(),
    var mlTransformMl: MdlMlTransform = MdlMlTransform(),
    var mlModelParameters: MdlMlModelParameters = MdlMlModelParameters(),

    var mlResponse: MdlMl = MdlMl(),
    var mlsResponse: MutableList<MdlMl> = mutableListOf(),
    var mlResponseTrainModel: MdlMlTrainResult = MdlMlTrainResult(),
    var mlResponseTransform: MdlMlTransform = MdlMlTransform()

    )

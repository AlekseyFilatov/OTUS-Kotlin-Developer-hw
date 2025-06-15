package api.kotlinproject.app.common

import api.kotlinproject.api.v1.models.*
import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.mappers.kmpmappers.fromTransport
import api.kotlinproject.mappers.kmpmappers.toTransportMl


import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ControllerV2Test {

    private val request = MlCreateRequest(
        ml = MlCreateObject(
            title = "forrest",
            description = "some description of some ml"
        ),
        debug = MlDebug(mode = MlRequestDebugMode.STUB, stub = MlRequestDebugStubs.SUCCESS)
    )

    private val appSettings: IMdlAppSettings = object : IMdlAppSettings {
        override val corSettings: MdlCorSettings = MdlCorSettings()
        override val processor: MdlMlProcessor = MdlMlProcessor(corSettings)
    }

    class TestApplicationCall(private val request: IRequest) {
        var res: IResponse? = null

        @Suppress("UNCHECKED_CAST")
        fun <T : IRequest> receive(): T = request as T
        fun respond(res: IResponse) {
            this.res = res
        }
    }

    private suspend fun TestApplicationCall.createMlKtor(appSettings: IMdlAppSettings) {
        val resp = appSettings.controllerHelper(
            { fromTransport(receive<MlCreateRequest>()) },
            { toTransportMl() },
            ControllerV2Test::class,
            "controller-v2-test"
        )
        respond(resp)
    }

    @Test
    fun ktorHelperTest() = runTest {
        val testApp = TestApplicationCall(request).apply { createMlKtor(appSettings) }
        val res = testApp.res as MlCreateResponse
        assertEquals(ResponseResult.SUCCESS, res.result)
    }
}

package api.kotlinproject.app.ktor.websocket

import api.kotlinproject.api.jackson.v1.models.*
import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.app.ktor.moduleJvm
import api.kotlinproject.common.MdlCorSettings
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import kotlinx.coroutines.withTimeout
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class V1WebsocketStubTest {

    @Test
    fun createStub() {
        val request = MlCreateRequest(
            ml = MlCreateObject(
                title = "forrest",
                description = "Модель forrest"
            ),
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(ResponseResult.SUCCESS, it.result)
        }
    }

    @Test
    fun readStub() {
        val request = MlReadRequest(
            ml = MlReadObject("forrest"),
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(ResponseResult.SUCCESS, it.result)
        }
    }

    @Test
    fun updateStub() {
        val request = MlUpdateRequest(
            ml = MlUpdateObject(
                title = "forrest"
            ),
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(ResponseResult.SUCCESS, it.result)
        }
    }

    @Test
    fun deleteStub() {
        val request = MlDeleteRequest(
            ml = MlDeleteObject(
                title = "forrest",
            ),
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(ResponseResult.SUCCESS, it.result)
        }
    }

    @Test
    fun searchStub() {
        val request = MlSearchRequest(
            mlFilter = MlSearchFilter(),
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request) {
            assertEquals(ResponseResult.SUCCESS, it.result)
        }
    }


    private inline fun <reified T> testMethod(
        request: IRequest,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {
        application { moduleJvm(MdlAppSettings(corSettings = MdlCorSettings())) }
        val client = createClient {
            install(WebSockets) {
                contentConverter = JacksonWebsocketContentConverter()
            }
        }

        client.webSocket("/v1/ws") {
            withTimeout(3000) {
                val response = receiveDeserialized<IResponse>() as T
                assertIs<MlInitResponse>(response)
            }
            sendSerialized(request)
            withTimeout(4000) {
                val response = receiveDeserialized<IResponse>() as T
                assertBlock(response)
            }
        }
    }
}
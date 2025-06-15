package api.kotlinproject.app.ktor.stub

import api.kotlinproject.api.jackson.v1.models.*
import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.app.ktor.moduleJvm
import api.kotlinproject.common.MdlCorSettings
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class V1MlStubApiTest {
    @Test
    fun create() = v1TestApplication(
        func = "create",
        request = MlCreateRequest(
            ml = MlCreateObject(
                title = "forrest",
                description = "ml model"
            ),
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<MlCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("forrest", responseObj.ml?.title)
    }

    @Test
    fun analyticml() = v1TestApplication(
        func = "analyticml",
        request = AnalyticMlReadRequest(
            ml = AnalyticMl(
                ticker = "NVDA",
                taskNumber = "1",
                dateStart = "2021-01-03",
                dateEnd = "2023-05-30",
                evalPivotPoint = 10,
                dateOffset = 2,
                modelParameters = ModelParameters(
                    learningRate = 0.05,
                    maxDepth = 8,
                    subSample = 0.8,
                    gamma = 1,
                    numRound = 500,
                    treeMethod = "gpu_hist",
                    refreshLeaf = 1,
                    processType = "default"
                )
            ),
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<AnalyticMlReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("1900-01-01", responseObj.ml?.dateTime)
    }

    @Test
    fun transformml() = v1TestApplication(
        func = "transformml",
        request = TransformMlUpdateRequest(
            ml = TransformMl(
                ticker = "NVDA",
                taskNumber = "1",
                dateStart = "2021-01-03",
                dateEnd = "2023-05-30",
                dateOffset = 2,
                batchSize = 1
            ),
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<TransformMlUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("NVDA", responseObj.ml?.ticker)
    }

    @Test
    fun read() = v1TestApplication(
        func = "read",
        request = MlReadRequest(
            ml = MlReadObject("forrest"),
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<MlReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("forrest", responseObj.ml?.title)
    }

    @Test
    fun update() = v1TestApplication(
        func = "update",
        request = MlUpdateRequest(
            ml = MlUpdateObject(
                title = "forrest"
            ),
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<MlUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("forrest", responseObj.ml?.title)
    }

    @Test
    fun delete() = v1TestApplication(
        func = "delete",
        request = MlDeleteRequest(
            ml = MlDeleteObject(
                title = "forrest",
            ),
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<MlDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("forrest", responseObj.ml?.title)
    }

    @Test
    fun search() = v1TestApplication(
        func = "search",
        request = MlSearchRequest(
            mlFilter = MlSearchFilter(),
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<MlSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("stub", responseObj.mls?.first()?.title)
    }
    private fun v1TestApplication(
        func: String,
        request: IRequest,
        function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { moduleJvm(MdlAppSettings(corSettings = MdlCorSettings())) }
        val client = createClient {
            install(ContentNegotiation) {
                jackson {
                    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                    enable(SerializationFeature.INDENT_OUTPUT)
                    writerWithDefaultPrettyPrinter()
                }
            }
        }
        val response = client.post("/v1/ml/$func") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        function(response)
    }
}

package api.kotlinproject.api.v1

import api.kotlinproject.api.jackson.v1.apiV1Mapper
import api.kotlinproject.api.jackson.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request = MlCreateRequest(
        "create",
        "1",
        workMode = MlRequestWorkMode(
            mode = MlRequestDebugMode.STUB,
            stub = MlRequestDebugStubs.SUCCESS
        ),
        ml = MlCreateObject(
            title = "forest",
            description = "ml description",
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"title\":\\s*\"forest\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as MlCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"Ml": null}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, MlCreateRequest::class.java)

        assertEquals(null, obj.ml)
    }
}

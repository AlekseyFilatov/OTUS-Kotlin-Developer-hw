package api.kotlinproject.api.v1

import api.kotlinproject.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationKMPTest {
    private val request: IRequest = MlCreateRequest(
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
        val json = apiV2Mapper.encodeToString(IRequest.serializer(), request)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"forest\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"requestType\":\\s*\"mlCreate\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(request)
        val obj = apiV2Mapper.decodeFromString<IRequest>(json) as MlCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"Ml": null}
        """.trimIndent()
        val obj = apiV2Mapper.decodeFromString<MlCreateRequest>(jsonString)

        assertEquals(null, obj.ml)
    }
}

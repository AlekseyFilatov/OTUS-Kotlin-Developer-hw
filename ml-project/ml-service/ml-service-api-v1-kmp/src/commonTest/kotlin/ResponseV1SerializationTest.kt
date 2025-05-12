package api.kotlinproject.api.v1

import api.kotlinproject.api.v1.models.IResponse
import api.kotlinproject.api.v1.models.MlCreateResponse
import api.kotlinproject.api.v1.models.MlResponseObject
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationKMPTest {
    private val response: IResponse = MlCreateResponse(
        ml = MlResponseObject(
            title = "forest",
            description = "ml description"
        )
    )

    @Test
    fun serialize() {
        val json = apiV2Mapper.encodeToString(response)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"forest\""))
        assertContains(json, Regex("\"responseType\":\\s*\"mlCreate\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(response)
        val obj = apiV2Mapper.decodeFromString<IResponse>(json) as MlCreateResponse

        assertEquals(response, obj)
    }
}

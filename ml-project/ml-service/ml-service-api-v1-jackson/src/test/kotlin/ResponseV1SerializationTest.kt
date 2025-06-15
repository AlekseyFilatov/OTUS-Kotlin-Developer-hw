package api.kotlinproject.api.v1

import api.kotlinproject.api.jackson.v1.apiV1Mapper
import api.kotlinproject.api.jackson.v1.models.IResponse
import api.kotlinproject.api.jackson.v1.models.MlCreateResponse
import api.kotlinproject.api.jackson.v1.models.MlResponseObject
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response = MlCreateResponse(
        ml = MlResponseObject(
            title = "forest",
            //requestId = "1",
            //responseType = "create",
            description = "ml description"
            //result = ResponseResult.SUCCESS,
            //errors = null
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"title\":\\s*\"forest\""))
        assertContains(json, Regex("\"responseType\":\\s*\"mlCreate\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as MlCreateResponse

        assertEquals(response, obj)
    }
}

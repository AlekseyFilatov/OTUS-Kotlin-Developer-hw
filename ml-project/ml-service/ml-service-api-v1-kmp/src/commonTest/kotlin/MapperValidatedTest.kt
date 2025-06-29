package api.kotlinproject.api.v1

import api.kotlinproject.api.v1.kmpmappers.fromTransportValidated
import api.kotlinproject.api.v1.models.MlCreateRequest
import api.kotlinproject.api.v1.models.MlDebug
import api.kotlinproject.api.v1.models.MlRequestDebugStubs
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.stubs.MdlStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperValidatedTest {
    @Test
    fun fromTransportValidated() {
        val req = MlCreateRequest(
            debug = MlDebug(
                stub = MlRequestDebugStubs.SUCCESS,
            ),
        )

        val context = MdlContext()
        context.fromTransportValidated(req)

        assertEquals(MdlStubs.SUCCESS, context.stubCase)
    }
}
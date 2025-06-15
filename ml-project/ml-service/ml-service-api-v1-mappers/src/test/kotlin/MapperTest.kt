

import api.kotlinproject.api.jackson.v1.models.*
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.*
import api.kotlinproject.common.stubs.MdlStubs
import api.kotlinproject.mappers.v1.jacksonmappers.fromTransport
import api.kotlinproject.mappers.v1.jacksonmappers.toTransportCreateMl
import api.kotlinproject.mappers.v1.jacksonmappers.toTransportMl
import api.kotlinproject.stubs.MdlMlStub
import org.junit.Test
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = MlCreateRequest(
            debug = MlDebug(
                mode = MlRequestDebugMode.STUB,
                stub = MlRequestDebugStubs.SUCCESS,
            ),
            ml = MdlMlStub.get().toTransportCreateMl()
        )
        val expected = MdlMlStub.prepareResult {
            title = "forrest"
        }

        val context = MdlContext()
        context.fromTransport(req)

        assertEquals(MdlStubs.SUCCESS, context.stubCase)
        assertEquals(MdlWorkMode.STUB, context.workMode)
        assertEquals(expected, context.mlRequest)
    }

    @Test
    fun toTransport() {
        val context = MdlContext(
            requestTitle = MdlRequestTitle("1234"),
            command = MdlCommand.CREATE,
            mlResponse = MdlMlStub.get(),
            errors = mutableListOf(
                MdlError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = MdlState.RUNNING,
        )

        val req = context.toTransportMl() as MlCreateResponse

        assertEquals(req.ml, MdlMlStub.get().toTransportMl())
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}

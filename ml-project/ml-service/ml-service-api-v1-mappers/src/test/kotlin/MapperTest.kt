

import api.kotlinproject.api.v1.models.*
import api.kotlinproject.common.MkplContext
import api.kotlinproject.common.models.*
import api.kotlinproject.common.stubs.MkplStubs
import api.kotlinproject.mappers.v1.fromTransport
import api.kotlinproject.mappers.v1.toTransportCreateMl
import api.kotlinproject.mappers.v1.toTransportMl
import api.kotlinproject.stubs.MkplMlStub
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
            ml = MkplMlStub.get().toTransportCreateMl()
        )
        val expected = MkplMlStub.prepareResult {
            title = "forest"
        }

        val context = MkplContext()
        context.fromTransport(req)

        assertEquals(MkplStubs.SUCCESS, context.stubCase)
        assertEquals(MkplWorkMode.STUB, context.workMode)
        assertEquals(expected, context.mlRequest)
    }

    @Test
    fun toTransport() {
        val context = MkplContext(
            requestId = MkplRequestId("1234"),
            command = MkplCommand.CREATE,
            mlResponse = MkplMlStub.get(),
            errors = mutableListOf(
                MkplError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = MkplState.RUNNING,
        )

        val req = context.toTransportMl() as MlCreateResponse

        assertEquals(req.ml, MkplMlStub.get().toTransportMl())
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}

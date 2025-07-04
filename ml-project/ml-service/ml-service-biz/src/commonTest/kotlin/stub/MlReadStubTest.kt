package api.kotlinproject.biz.stub

import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.*
import api.kotlinproject.common.stubs.MdlStubs
import api.kotlinproject.stubs.MdlMlStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MlReadStubTest {

    private val processor = MdlMlProcessor()
    val title = MdlMlTitle("forrest")

    @Test
    fun read() = runTest {

        val ctx = MdlContext(
            command = MdlCommand.READ,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.SUCCESS,
            mlRequest = MdlMl(
                title = title.asString(),
            ),
        )
        processor.exec(ctx)
        with (MdlMlStub.get()) {
            //assertEquals(id, ctx.mlResponse.id)
            assertEquals(title, ctx.mlResponse.title)
            assertEquals(description, ctx.mlResponse.description)
            //assertEquals(mlType, ctx.mlResponse.mlType)
            //assertEquals(visibility, ctx.mlResponse.visibility)
        }
    }

    @Test
    fun badTitle() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.READ,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.BAD_TITLE,
            mlRequest = MdlMl(),
        )
        processor.exec(ctx)
        assertEquals(MdlMl(), ctx.mlResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.READ,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.DB_ERROR,
            mlRequest = MdlMl(
                title = title.asString(),
            ),
        )
        processor.exec(ctx)
        assertEquals(MdlMl(), ctx.mlResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.READ,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.BAD_TITLE,
            mlRequest = MdlMl(
                title = title.asString(),
            ),
        )
        processor.exec(ctx)
        assertEquals(MdlMl(), ctx.mlResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
    }
}

package api.kotlinproject.biz.stub

import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.*
import api.kotlinproject.common.stubs.MdlStubs
import api.kotlinproject.stubs.MdlMlStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class MlSearchStubTest {

    private val processor = MdlMlProcessor()
    val filter = MdlMlFilter(searchString = "stub")

    @Test
    fun read() = runTest {

        val ctx = MdlContext(
            command = MdlCommand.SEARCH,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.SUCCESS,
            mlFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.mlsResponse.size > 1)
        val first = ctx.mlsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title?.contains(filter.searchString) == true)
        with (MdlMlStub.get()) {
            assertEquals("stub", first.title)
        }
    }

    @Test
    fun databaseError() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.SEARCH,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.DB_ERROR,
            mlFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(MdlMl(), ctx.mlResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.SEARCH,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.BAD_TITLE,
            mlFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(MdlMl(), ctx.mlResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
    }
}

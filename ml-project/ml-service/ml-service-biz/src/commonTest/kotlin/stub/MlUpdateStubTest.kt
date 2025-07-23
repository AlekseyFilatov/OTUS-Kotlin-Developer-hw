package api.kotlinproject.biz.stub

import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.*
import api.kotlinproject.common.stubs.MdlStubs
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MlUpdateStubTest {

    private val processor = MdlMlProcessor()
   // val id = MdlMlTitle("forrest")
    val title = "title forrest"
    val description = "desc forrest"

    @Test
    fun create() = runTest {

        val ctx = MdlContext(
            command = MdlCommand.UPDATE,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.SUCCESS,
            mlRequest = MdlMl(
              //  id = id,
                title = title,
                description = description,
            ),
        )
        processor.exec(ctx)
        //assertEquals(id, ctx.mlResponse.id)
        assertEquals(title, ctx.mlResponse.title)
        assertEquals(description, ctx.mlResponse.description)

    }

/*    @Test
    fun badId() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.UPDATE,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.BAD_TITLE,
            mlRequest = MdlMl(),
        )
        processor.exec(ctx)
        assertEquals(MdlMl(), ctx.mlResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }*/

    @Test
    fun badTitle() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.UPDATE,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.BAD_TITLE,
            mlRequest = MdlMl(
                //id = id,
                title = "",
                description = description,
            ),
        )
        processor.exec(ctx)
        assertEquals(MdlMl(), ctx.mlResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badDescription() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.UPDATE,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.BAD_DESCRIPTION,
            mlRequest = MdlMl(
                title = title,
                description = "",
                id = MdlMlId.NONE
            ),
        )
        processor.exec(ctx)
        assertEquals(MdlMl(), ctx.mlResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.UPDATE,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.DB_ERROR,
            mlRequest = MdlMl(
                title = title,
            ),
        )
        processor.exec(ctx)
        assertEquals(MdlMl(), ctx.mlResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.UPDATE,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.BAD_SEARCH_STRING,
            mlRequest = MdlMl(
               // id = id,
                title = title,
                description = description,
            ),
        )
        processor.exec(ctx)
        assertEquals(MdlMl(), ctx.mlResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}

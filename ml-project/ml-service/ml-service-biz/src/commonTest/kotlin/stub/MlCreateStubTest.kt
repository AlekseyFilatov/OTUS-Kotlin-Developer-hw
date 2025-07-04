package api.kotlinproject.biz.stub

import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.common.stubs.MdlStubs
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MlCreateStubTest {

    private val processor = MdlMlProcessor()

    val title = "title forrest"
    val description = "desc forrest"

    @Test
    fun create() = runTest {

        val ctx = MdlContext(
            command = MdlCommand.CREATE,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.SUCCESS,
            mlRequest = MdlMl(
                //id = id,
                title = title,
                description = description,
              //  adType = dealSide,
               // visibility = visibility,
            ),
        )
        processor.exec(ctx)
        //assertEquals(MdlMlStub.get().id, ctx.mlResponse.id)
        assertEquals(title, ctx.mlResponse.title)
        assertEquals(description, ctx.mlResponse.description)
       // assertEquals(dealSide, ctx.adResponse.adType)
       // assertEquals(visibility, ctx.adResponse.visibility)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.CREATE,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.BAD_TITLE,
            mlRequest = MdlMl(
             //   id = id,
                title = "",
                description = description,
              //  adType = dealSide,
              //  visibility = visibility,
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
            command = MdlCommand.CREATE,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.BAD_DESCRIPTION,
            mlRequest = MdlMl(
              //  id = id,
                title = title,
                description = "",
              //  adType = dealSide,
              //  visibility = visibility,
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
            command = MdlCommand.CREATE,
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
            command = MdlCommand.CREATE,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.BAD_TITLE,
            mlRequest = MdlMl(
               // id = id,
                title = title,
                description = description,
               // adType = dealSide,
               // visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(MdlMl(), ctx.mlResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}

package api.kotlinproject.biz.validation

import kotlinx.coroutines.test.runTest
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlMlFilter
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.models.MdlWorkMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizValidationSearchTest: BaseBizValidationTest() {
    override val command = MdlCommand.SEARCH

    @Test
    fun correctEmpty() = runTest {
        val ctx = MdlContext(
            command = command,
            state = MdlState.NONE,
            workMode = MdlWorkMode.TEST,
            mlFilterRequest = MdlMlFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(MdlState.FAILING, ctx.state)
    }
}

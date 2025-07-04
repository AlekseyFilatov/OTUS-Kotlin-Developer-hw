package api.kotlinproject.biz.validation

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlMlFilter
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.cor.rootChain
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateTitleHasContentTest {
    @Test
    fun emptyString() = runTest {
        val ctx = MdlContext(state = MdlState.RUNNING, mlValidating = MdlMl(title = ""))
        chain.exec(ctx)
        assertEquals(MdlState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun noContent() = runTest {
        val ctx = MdlContext(state = MdlState.RUNNING, mlValidating = MdlMl(title = "12!@#$%^&*()_+-="))
        chain.exec(ctx)
        assertEquals(MdlState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-title-noContent", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = MdlContext(state = MdlState.RUNNING, mlFilterValidating = MdlMlFilter(searchString = "Ж"))
        chain.exec(ctx)
        assertEquals(MdlState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    companion object {
        val chain = rootChain {
            validateTitleHasContent("")
        }.build()
    }
}

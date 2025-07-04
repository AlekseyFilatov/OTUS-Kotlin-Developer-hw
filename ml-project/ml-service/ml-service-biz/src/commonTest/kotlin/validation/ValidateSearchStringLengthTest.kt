package api.kotlinproject.biz.validation

import kotlinx.coroutines.test.runTest
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlMlFilter
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.cor.rootChain
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateSearchStringLengthTest {
    @Test
    fun emptyString() = runTest {
        val ctx = MdlContext(state = MdlState.RUNNING, mlFilterValidating = MdlMlFilter(searchString = ""))
        chain.exec(ctx)
        assertEquals(MdlState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun blankString() = runTest {
        val ctx = MdlContext(state = MdlState.RUNNING, mlFilterValidating = MdlMlFilter(searchString = "  "))
        chain.exec(ctx)
        assertEquals(MdlState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun shortString() = runTest {
        val ctx = MdlContext(state = MdlState.RUNNING, mlFilterValidating = MdlMlFilter(searchString = "12"))
        chain.exec(ctx)
        assertEquals(MdlState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooShort", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = MdlContext(state = MdlState.RUNNING, mlFilterValidating = MdlMlFilter(searchString = "123"))
        chain.exec(ctx)
        assertEquals(MdlState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun longString() = runTest {
        val ctx = MdlContext(state = MdlState.RUNNING, mlFilterValidating = MdlMlFilter(searchString = "12".repeat(51)))
        chain.exec(ctx)
        assertEquals(MdlState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooLong", ctx.errors.first().code)
    }

    companion object {
        val chain = rootChain {
            validateSearchStringLength("")
        }.build()
    }
}

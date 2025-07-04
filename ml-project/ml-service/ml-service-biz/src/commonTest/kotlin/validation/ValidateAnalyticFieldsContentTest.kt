package api.kotlinproject.biz.validation

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.*
import api.kotlinproject.cor.rootChain
import biz.validation.validateAnalyticHasContent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateAnalyticFieldsContentTest {
    @Test
    fun emptyString() = runTest {
        val ctx = MdlContext(state = MdlState.RUNNING, mlAnalyticValidating = MdlMlAnalytic(ticker = MdlMlTicker("NVDA"), taskNumber = MdlMlTaskNumber("123"), dateStart = "1900-01-01", dateEnd = "2025-01-01", evalPivotPoint = 0.toLong(),
            modelParameters = MdlMlModelParameters(),
            batchSize = 1.toLong(),
            dateOffset = 1.toLong()))
        chain.exec(ctx)
        assertEquals(MdlState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun noContent() = runTest {
        val ctx = MdlContext(state = MdlState.RUNNING, mlAnalyticValidating = MdlMlAnalytic(ticker = MdlMlTicker("12!@#$%^&*()_+-=")))
        chain.exec(ctx)
        assertEquals(MdlState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-analytic fields-noContent", ctx.errors.first().code)
    }

    companion object {
        val chain = rootChain {
            validateAnalyticHasContent("")
        }.build()
    }
}

package api.kotlinproject.stubs

import api.kotlinproject.common.models.MdlMlAnalytic
import api.kotlinproject.common.stubs.MdlMlAnalyticStubs


object MdlMlAnalyticStub {
    fun get(): MdlMlAnalytic = MdlMlAnalyticStubs.ML_Analytic.copy()

    fun prepareResult(block: MdlMlAnalytic.() -> Unit): MdlMlAnalytic = get().apply(block)
}
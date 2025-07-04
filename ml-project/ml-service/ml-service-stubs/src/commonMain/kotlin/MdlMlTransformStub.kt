package api.kotlinproject.stubs

import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.stubs.MdlMlTransformStubs

object MdlMlTransformStub {
    fun get(): MdlMlTransform = MdlMlTransformStubs.ML_Transform.copy()

    fun prepareResult(block: MdlMlTransform.() -> Unit): MdlMlTransform = get().apply(block)
}
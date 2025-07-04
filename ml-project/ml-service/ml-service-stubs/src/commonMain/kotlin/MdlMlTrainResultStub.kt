package api.kotlinproject.stubs

import api.kotlinproject.common.models.MdlMlTrainResult
import api.kotlinproject.common.stubs.MdlMlTrainResultStubs

object MdlMlTrainResultStub {
    fun get(): MdlMlTrainResult = MdlMlTrainResultStubs.ML_TrainResult.copy()

    fun prepareResult(block: MdlMlTrainResult.() -> Unit): MdlMlTrainResult = get().apply(block)
}
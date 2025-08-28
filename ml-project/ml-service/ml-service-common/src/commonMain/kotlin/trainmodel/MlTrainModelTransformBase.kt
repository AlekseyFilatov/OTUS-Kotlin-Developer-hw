package api.kotlinproject.common.trainmodel

import api.kotlinproject.common.helpers.errorSystem

abstract class MlTrainModelTransformBase: ITrainModelMlTransform {

    protected suspend fun tryTrainModelMlTransformMethod(block: suspend () -> ITrainModelMlTransformResponse) = try {
        block()
    } catch (e: Throwable) {
        TrainModelMlTransformResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryTrainModelMlsTransformMethod(block: suspend () -> ITrainModelMlsTransformResponse) = try {
        block()
    } catch (e: Throwable) {
        TrainModelMlsTransformResponseErr(errorSystem("methodException", e = e))
    }

}

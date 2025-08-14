package api.kotlinproject.common.trainmodel

import api.kotlinproject.common.helpers.errorSystem

abstract class MlTrainModelTrainResultBase: ITrainModelMlTrainResult {

    protected suspend fun tryTrainModelMlTrainResultMethod(block: suspend () -> ITrainModelMlTrainResultResponse) = try {
        block()
    } catch (e: Throwable) {
        TrainModelMlTrainResultResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryTrainModelMlsTrainResultMethod(block: suspend () -> ITrainModelMlsTrainResultResponse) = try {
        block()
    } catch (e: Throwable) {
        TrainModelMlsTrainResultResponseErr(errorSystem("methodException", e = e))
    }

}

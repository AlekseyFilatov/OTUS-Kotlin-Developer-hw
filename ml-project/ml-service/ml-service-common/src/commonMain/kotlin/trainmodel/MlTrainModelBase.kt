package api.kotlinproject.common.trainmodel

import api.kotlinproject.common.helpers.errorSystem

abstract class MlTrainModelBase: ITrainModelMl {

    protected suspend fun tryTrainModelMlMethod(block: suspend () -> ITrainModelMlResponse) = try {
        block()
    } catch (e: Throwable) {
        TrainModelMlResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryTrainModelMlsMethod(block: suspend () -> ITrainModelMlsResponse) = try {
        block()
    } catch (e: Throwable) {
        TrainModelMlsResponseErr(errorSystem("methodException", e = e))
    }

}

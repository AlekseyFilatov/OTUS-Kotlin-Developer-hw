package api.kotlinproject.common.repo

import api.kotlinproject.common.helpers.errorSystem

abstract class MlRepoTrainResultBase: IRepoMlTrainResult {

    protected suspend fun tryMlTrainResultMethod(block: suspend () -> IDbMlTrainResultResponse) = try {
        block()
    } catch (e: Throwable) {
        DbMlTrainResultResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryMlsTrainResultMethod(block: suspend () -> IDbMlsTrainResultResponse) = try {
        block()
    } catch (e: Throwable) {
        DbMlsTrainResultResponseErr(errorSystem("methodException", e = e))
    }

}

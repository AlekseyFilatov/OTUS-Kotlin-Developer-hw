package api.kotlinproject.common.repo

import api.kotlinproject.common.helpers.errorSystem

abstract class MlRepoTransformBase: IRepoMlTransform {

    protected suspend fun tryMlTransformMethod(block: suspend () -> IDbMlTransformResponse) = try {
        block()
    } catch (e: Throwable) {
        DbMlTransformResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryMlsTransformMethod(block: suspend () -> IDbMlsTransformResponse) = try {
        block()
    } catch (e: Throwable) {
        DbMlsTransformResponseErr(errorSystem("methodException", e = e))
    }

}

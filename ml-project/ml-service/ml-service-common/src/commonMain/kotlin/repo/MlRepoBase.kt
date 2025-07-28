package api.kotlinproject.common.repo

import api.kotlinproject.common.helpers.errorSystem

abstract class MlRepoBase: IRepoMl {

    protected suspend fun tryMlMethod(block: suspend () -> IDbMlResponse) = try {
        block()
    } catch (e: Throwable) {
        DbMlResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryMlsMethod(block: suspend () -> IDbMlsResponse) = try {
        block()
    } catch (e: Throwable) {
        DbMlsResponseErr(errorSystem("methodException", e = e))
    }

}

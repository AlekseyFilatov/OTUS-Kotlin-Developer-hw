package api.kotlinproject.biz.repo

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.repo.DbMlIdRequest
import api.kotlinproject.common.repo.DbMlResponseErr
import api.kotlinproject.common.repo.DbMlResponseErrWithData
import api.kotlinproject.common.repo.DbMlResponseOk
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == MdlState.RUNNING }
    handle {
        val request = DbMlIdRequest(mlRepoPrepare)
        when(val result = mlRepo.deleteMl(request)) {
            is DbMlResponseOk -> mlRepoDone = result.data
            is DbMlResponseErr -> {
                fail(result.errors)
                mlRepoDone = mlRepoRead
            }
            is DbMlResponseErrWithData -> {
                fail(result.errors)
                mlRepoDone = result.data
            }
        }
    }
}

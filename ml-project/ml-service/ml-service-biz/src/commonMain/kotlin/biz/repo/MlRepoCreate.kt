package api.kotlinproject.biz.repo

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.repo.DbMlRequest
import api.kotlinproject.common.repo.DbMlResponseErr
import api.kotlinproject.common.repo.DbMlResponseErrWithData
import api.kotlinproject.common.repo.DbMlResponseOk
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление модели в БД"
    on { state == MdlState.RUNNING }
    handle {
        val request = DbMlRequest(mlRepoPrepare)
        when(val result = mlRepo.createMl(request)) {
            is DbMlResponseOk -> mlRepoDone = result.data
            is DbMlResponseErr -> fail(result.errors)
            is DbMlResponseErrWithData -> fail(result.errors)
        }
    }
}

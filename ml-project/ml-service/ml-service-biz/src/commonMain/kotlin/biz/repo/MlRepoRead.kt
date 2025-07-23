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

fun ICorChainDsl<MdlContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение модели из БД"
    on { state == MdlState.RUNNING }
    handle {
        val request = DbMlIdRequest(mlValidated)
        when(val result = mlRepo.readMl(request)) {
            is DbMlResponseOk -> mlRepoRead = result.data
            is DbMlResponseErr -> fail(result.errors)
            is DbMlResponseErrWithData -> {
                fail(result.errors)
                mlRepoRead = result.data
            }
        }
    }
}

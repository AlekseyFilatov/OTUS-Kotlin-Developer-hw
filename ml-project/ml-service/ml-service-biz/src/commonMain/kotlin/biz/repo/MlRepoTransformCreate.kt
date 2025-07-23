package api.kotlinproject.biz.repo

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.repo.DbMlTransformRequest
import api.kotlinproject.common.repo.DbMlTransformResponseErr
import api.kotlinproject.common.repo.DbMlTransformResponseErrWithData
import api.kotlinproject.common.repo.DbMlTransformResponseOk
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.repoCreateTransform(title: String) = worker {
    this.title = title
    description = "Добавление модели в БД"
    on { state == MdlState.RUNNING }
    handle {
        val request = DbMlTransformRequest(mlRepoTransformPrepare)
        when(val result = mlRepoTransform.createMlTransform(request)) {
            is DbMlTransformResponseOk -> mlRepoTransformDone = result.data
            is DbMlTransformResponseErr -> fail(result.errors)
            is DbMlTransformResponseErrWithData -> fail(result.errors)
        }
    }
}

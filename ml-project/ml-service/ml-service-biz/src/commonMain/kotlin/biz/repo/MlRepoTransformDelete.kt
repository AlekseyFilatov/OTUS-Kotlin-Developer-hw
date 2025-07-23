package api.kotlinproject.biz.repo

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.repo.DbMlIdTransformRequest
import api.kotlinproject.common.repo.DbMlTransformResponseErr
import api.kotlinproject.common.repo.DbMlTransformResponseErrWithData
import api.kotlinproject.common.repo.DbMlTransformResponseOk
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.repoDeleteTransform(title: String) = worker {
    this.title = title
    description = "Удаление модели из БД по ID"
    on { state == MdlState.RUNNING }
    handle {
        val request = DbMlIdTransformRequest(mlRepoTransformPrepare)
        when(val result = mlRepoTransform.deleteMlTransform(request)) {
            is DbMlTransformResponseOk -> mlRepoTransformDone = result.data
            is DbMlTransformResponseErr -> {
                fail(result.errors)
                mlRepoTransformDone = mlRepoTransformRead
            }
            is DbMlTransformResponseErrWithData -> {
                fail(result.errors)
                mlRepoTransformDone = result.data
            }
        }
    }
}

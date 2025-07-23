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

fun ICorChainDsl<MdlContext>.repoReadTransform(title: String) = worker {
    this.title = title
    description = "Чтение модели из БД"
    on { state == MdlState.RUNNING }
    handle {
        val request = DbMlIdTransformRequest(mlTransformMl)
        when(val result = mlRepoTransform.readMlTransform(request)) {
            is DbMlTransformResponseOk -> mlRepoTransformRead = result.data
            is DbMlTransformResponseErr -> fail(result.errors)
            is DbMlTransformResponseErrWithData -> {
                fail(result.errors)
                mlRepoTransformRead = result.data
            }
        }
    }
}
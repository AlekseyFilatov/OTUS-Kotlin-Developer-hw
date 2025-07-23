package api.kotlinproject.biz.repo

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.repo.DbMlIdTrainResultRequest
import api.kotlinproject.common.repo.DbMlTrainResultResponseErr
import api.kotlinproject.common.repo.DbMlTrainResultResponseErrWithData
import api.kotlinproject.common.repo.DbMlTrainResultResponseOk
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.repoReadTrainResult(title: String) = worker {
    this.title = title
    description = "Чтение модели из БД"
    on { state == MdlState.RUNNING }
    handle {
        val request = DbMlIdTrainResultRequest(mlTrainResultMl)
        when(val result = mlRepoTrainResult.readMlTrainResult(request)) {
            is DbMlTrainResultResponseOk -> mlRepoTrainResultRead = result.data
            is DbMlTrainResultResponseErr -> fail(result.errors)
            is DbMlTrainResultResponseErrWithData -> {
                fail(result.errors)
                mlRepoTrainResultRead = result.data
            }
        }
    }
}

package api.kotlinproject.biz.repo

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.repo.DbMlTrainResultRequest
import api.kotlinproject.common.repo.DbMlTrainResultResponseErr
import api.kotlinproject.common.repo.DbMlTrainResultResponseErrWithData
import api.kotlinproject.common.repo.DbMlTrainResultResponseOk
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.repoCreateTrainResult(title: String) = worker {
    this.title = title
    description = "Добавление модели в БД"
    on { state == MdlState.RUNNING }
    handle {
        val request = DbMlTrainResultRequest(mlRepoTrainResultPrepare)
        when(val result = mlRepoTrainResult.createMlTrainResult(request)) {
            is DbMlTrainResultResponseOk -> mlRepoTrainResultDone = result.data
            is DbMlTrainResultResponseErr -> fail(result.errors)
            is DbMlTrainResultResponseErrWithData -> fail(result.errors)
        }
    }
}

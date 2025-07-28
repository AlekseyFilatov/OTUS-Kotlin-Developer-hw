package api.kotlinproject.biz.repo

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.repo.DbMlTrainResultResponseErr
import api.kotlinproject.common.repo.DbMlTrainResultResponseErrWithData
import api.kotlinproject.common.repo.DbMlTrainResultResponseOk
import api.kotlinproject.common.repo.DbMlIdTrainResultRequest
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.repoDeleteTrainResult(title: String) = worker {
    this.title = title
    description = "Удаление модели из БД по ID"
    on { state == MdlState.RUNNING }
    handle {
        val request = DbMlIdTrainResultRequest(mlRepoTrainResultPrepare)
        when(val result = mlRepoTrainResult.deleteMlTrainResult(request)) {
            is DbMlTrainResultResponseOk -> mlRepoTrainResultDone = result.data
            is DbMlTrainResultResponseErr -> {
                fail(result.errors)
                mlRepoTrainResultDone = mlRepoTrainResultRead
            }
            is DbMlTrainResultResponseErrWithData -> {
                fail(result.errors)
                mlRepoTrainResultDone = result.data
            }
        }
    }
}

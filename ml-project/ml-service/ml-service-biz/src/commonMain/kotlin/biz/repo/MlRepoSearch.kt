package api.kotlinproject.biz.repo

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.fail
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.repo.DbMlFilterRequest
import api.kotlinproject.common.repo.DbMlsResponseErr
import api.kotlinproject.common.repo.DbMlsResponseOk
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == MdlState.RUNNING }
    handle {
        val request = DbMlFilterRequest(
            titleFilter = mlFilterValidated.searchString,
           // ownerId = mlFilterValidated.ownerId,

        )
        when(val result = mlRepo.searchMl(request)) {
            is DbMlsResponseOk -> mlsRepoDone = result.data.toMutableList()
            is DbMlsResponseErr -> fail(result.errors)
        }
    }
}

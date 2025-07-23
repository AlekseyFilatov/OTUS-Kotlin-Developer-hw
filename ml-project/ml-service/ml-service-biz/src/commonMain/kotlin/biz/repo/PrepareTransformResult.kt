package api.kotlinproject.biz.repo

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.prepareTransformResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != MdlWorkMode.STUB }
    handle {
        mlResponseTransform = mlRepoTransformDone
        mlsResponseTransform = mlsRepoTransformDone
        state = when (val st = state) {
            MdlState.RUNNING -> MdlState.FINISHING
            else -> st
        }
    }
}

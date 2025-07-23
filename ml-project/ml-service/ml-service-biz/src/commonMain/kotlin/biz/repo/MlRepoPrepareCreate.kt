package api.kotlinproject.biz.repo

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == MdlState.RUNNING }
    handle {
        mlRepoPrepare = mlValidated.deepCopy()
        // TODO будет реализовано в занятии по управлению пользвателями
        //mlRepoPrepare.ownerId = MdlUserId.NONE
    }
}

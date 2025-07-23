package api.kotlinproject.biz.repo

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == MdlState.RUNNING }
    handle {
        mlRepoPrepare = mlRepoRead.deepCopy().apply {
            this.title = mlValidated.title
            this.description = mlValidated.description
            this.id = mlValidated.id
        }
    }
}

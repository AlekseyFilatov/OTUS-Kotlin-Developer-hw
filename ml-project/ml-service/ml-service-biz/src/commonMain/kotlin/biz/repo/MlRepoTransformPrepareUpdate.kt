package api.kotlinproject.biz.repo

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker

fun ICorChainDsl<MdlContext>.repoPrepareUpdateTransform(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == MdlState.RUNNING }
    handle {
        mlRepoTransformPrepare = mlRepoTransformRead.deepCopy().apply {
            this.id = mlTransformMl.id
        }
    }
}

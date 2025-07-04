package api.kotlinproject.biz.stubs

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.stubs.MdlStubs
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker
import api.kotlinproject.logging.common.LogLevel
import api.kotlinproject.stubs.MdlMlStub

fun ICorChainDsl<MdlContext>.stubSearchSuccess(title: String, corSettings: MdlCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для поиска модели
    """.trimIndent()
    on { stubCase == MdlStubs.SUCCESS && state == MdlState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubOffersSuccess")
    handle {
        logger.doWithLogging(id = this.requestTitle.asString(), LogLevel.DEBUG) {
            state = MdlState.FINISHING
            mlsResponse.addAll(MdlMlStub.prepareSearchList(mlFilterRequest.searchString))
        }
    }
}

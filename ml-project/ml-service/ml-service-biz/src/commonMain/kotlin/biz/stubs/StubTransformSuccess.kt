package biz.stubs

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.MdlTransformCorrSettings
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.stubs.MdlStubs
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker
import api.kotlinproject.logging.common.LogLevel
import api.kotlinproject.stubs.MdlMlTransformStub


fun ICorChainDsl<MdlContext>.stubTransformSuccess(title: String, corSettings: MdlTransformCorrSettings) = worker {
    this.title = title
    this.description = """
        Кейс для успешного получения результата повторной тренировки модели
    """.trimIndent()
    on { stubCase == MdlStubs.SUCCESS && state == MdlState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubOffersSuccess")
    handle {
        logger.doWithLogging(id = this.requestTitle.asString(), LogLevel.DEBUG) {
            state = MdlState.FINISHING
            val stub = MdlMlTransformStub
                .prepareResult {
                    mlTransformMl.ticker.takeIf { it?.isNotBlank() == true }.also { this.ticker = it }
                    mlTransformMl.taskNumber.takeIf { it?.isNotBlank() == true }.also { this.taskNumber = it }
                    mlTransformMl.dateStart.takeIf { it?.isNotBlank() == true }.also { this.dateStart = it }
                    mlTransformMl.dateEnd.takeIf { it?.isNotBlank() == true }.also { this.dateEnd = it }
                }
            mlTransformMl = stub
        }
    }
}
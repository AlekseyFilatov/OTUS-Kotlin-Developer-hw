package biz.stubs

import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.stubs.MdlStubs
import api.kotlinproject.cor.ICorChainDsl
import api.kotlinproject.cor.worker
import api.kotlinproject.logging.common.LogLevel
import api.kotlinproject.stubs.MdlMlAnalyticStub

fun ICorChainDsl<MdlContext>.stubAnalyticSuccess(title: String, corSettings: MdlCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс для успешного получения результата тренировки модели
    """.trimIndent()
    on { stubCase == MdlStubs.SUCCESS && state == MdlState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubOffersSuccess")
    handle {
        logger.doWithLogging(id = this.requestTitle.asString(), LogLevel.DEBUG) {
            state = MdlState.FINISHING
            val stub = MdlMlAnalyticStub
                .prepareResult {
                    /*Contract @OptIn(ExperimentalContracts::class)*/
                    mlAnalyticMl.ticker.takeIf { it.asString().isNotBlank() }.also {
                        if (it != null) {
                            this.ticker = it
                        }
                    }
                    mlAnalyticMl.taskNumber.takeIf { it.asString().isNotBlank() }.also {
                        if (it != null) {
                            this.taskNumber = it
                        }
                    }
                    mlAnalyticMl.dateStart.takeIf { it?.isNotBlank() == true }.also { this.dateStart = it }
                    mlAnalyticMl.dateEnd.takeIf { it?.isNotBlank() == true }.also { this.dateEnd = it }
                    /*Contract @OptIn(ExperimentalContracts::class)*/
                    mlAnalyticMl.modelParameters.treeMethod.takeIf { it.isNotBlank() }.also {
                        if (it != null) {
                            this.modelParameters.treeMethod = it
                        }
                    }
                    mlAnalyticMl.modelParameters.processType.takeIf { it.isNotBlank() }.also {
                        if (it != null) {
                            this.modelParameters.processType = it
                        }
                    }
                    mlAnalyticMl.modelParameters.updater.takeIf { it.isNotBlank() }.also {
                        if (it != null) {
                            this.modelParameters.updater = it
                        }
                    }
            }
            mlAnalyticMl = stub
        }
    }
}
package api.kotlinproject.app.ktor.plugins

import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.app.ktor.base.KtorWsSessionRepo
import api.kotlinproject.backend.repository.inmemory.MlRepoStub
import api.kotlinproject.backend.repository.inmemory.MlRepoTrainResultStub
import api.kotlinproject.backend.repository.inmemory.MlRepoTransformStub
import api.kotlinproject.biz.MdlMlAnalyticProcessor
import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.common.MdlTrainResultCorrSettings
import api.kotlinproject.common.MdlTransformCorrSettings
import io.ktor.server.application.*

fun Application.initAppSettings(): MdlAppSettings {
    val corSettings = MdlCorSettings(
        loggerProvider = getLoggerProviderConf(),
        wsSessions = KtorWsSessionRepo(),
        repoTest = getDatabaseConf(MlDbType.TEST),
        repoProd = getDatabaseConf(MlDbType.PROD),
        repoStub = MlRepoStub(),
    )
    val corSettingsTrainResult = MdlTrainResultCorrSettings(
        loggerProvider = getLoggerProviderConf(),
        wsTrainResultSessions = KtorWsSessionRepo(),
        repoTestTrainResult = getDatabaseTrainResultConf(MlDbType.TEST),
        repoProdTrainResult = getDatabaseTrainResultConf(MlDbType.PROD),
        repoStubTrainResult = MlRepoTrainResultStub(),
    )
    val corSettingsTransform = MdlTransformCorrSettings(
        loggerProvider = getLoggerProviderConf(),
        wsSessionsTransform = KtorWsSessionRepo(),
        repoTestTransform = getDatabaseTransformConf(MlDbType.TEST),
        repoProdTransform = getDatabaseTransformConf(MlDbType.PROD),
        repoStubTransform = MlRepoTransformStub(),
    )
    return MdlAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = MdlMlProcessor(corSettings),
        corSettingsTrainResult = corSettingsTrainResult,
        corSettingsTransform = corSettingsTransform,
        processorAnalytic = MdlMlAnalyticProcessor(corSettingsTrainResult)
    )
}

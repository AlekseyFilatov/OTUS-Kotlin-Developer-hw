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
import api.kotlinproject.common.trainmodel.MlTrainModelType
import io.ktor.server.application.*

fun Application.initAppSettings(): MdlAppSettings {
    val corSettings = MdlCorSettings(
        loggerProvider = getLoggerProviderConf(),
        wsSessions = KtorWsSessionRepo(),
        repoTest = getDatabaseConf(MlDbType.TEST),
        repoProd = getDatabaseConf(MlDbType.PROD),
        repoStub = MlRepoStub(),
        modelXGBoost = getTrainModel(MlTrainModelType.XGBoost),
        modelStub = getTrainModel(MlTrainModelType.STUB),
        modelForest = getTrainModel(MlTrainModelType.FOREST),
        modelRapids = getTrainModel(MlTrainModelType.RAPIDS),
    )
    val corSettingsTrainResult = MdlTrainResultCorrSettings(
        loggerProvider = getLoggerProviderConf(),
        wsTrainResultSessions = KtorWsSessionRepo(),
        repoTestTrainResult = getDatabaseTrainResultConf(MlDbType.TEST),
        repoProdTrainResult = getDatabaseTrainResultConf(MlDbType.PROD),
        repoStubTrainResult = MlRepoTrainResultStub(),
        modelXGBoostTrainResult = getTrainModelTrainResult(MlTrainModelType.XGBoost),
        modelStubTrainResult = getTrainModelTrainResult(MlTrainModelType.STUB),
        modelForestTrainResult = getTrainModelTrainResult(MlTrainModelType.FOREST),
        modelRapidsTrainResult = getTrainModelTrainResult(MlTrainModelType.RAPIDS),
    )
    val corSettingsTransform = MdlTransformCorrSettings(
        loggerProvider = getLoggerProviderConf(),
        wsSessionsTransform = KtorWsSessionRepo(),
        repoTestTransform = getDatabaseTransformConf(MlDbType.TEST),
        repoProdTransform = getDatabaseTransformConf(MlDbType.PROD),
        repoStubTransform = MlRepoTransformStub(),
        modelXGBoostTransform = getTrainModelTransform(MlTrainModelType.XGBoost),
        modelStubTransform = getTrainModelTransform(MlTrainModelType.STUB),
        modelForestTransform = getTrainModelTransform(MlTrainModelType.FOREST),
        modelRapidsTransform = getTrainModelTransform(MlTrainModelType.RAPIDS),
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

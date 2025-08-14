package api.kotlinproject.common

import api.kotlinproject.common.models.*
import api.kotlinproject.common.repo.IRepoMl
import api.kotlinproject.common.repo.IRepoMlTrainResult
import api.kotlinproject.common.repo.IRepoMlTransform
import api.kotlinproject.common.stubs.MdlStubs
import api.kotlinproject.common.trainmodel.ITrainModelMl
import api.kotlinproject.common.trainmodel.ITrainModelMlTrainResult
import api.kotlinproject.common.trainmodel.ITrainModelMlTransform
import api.kotlinproject.common.ws.IMdlWsSession
import kotlinx.datetime.Instant

data class MdlContext constructor(
    var command: MdlCommand = MdlCommand.NONE,
    var state: MdlState = MdlState.NONE,
    val errors: MutableList<MdlError> = mutableListOf(),
    var wsSession: IMdlWsSession = IMdlWsSession.NONE,

    var corSettings: MdlCorSettings = MdlCorSettings(),
    var corSettingsTrainResult: MdlTrainResultCorrSettings = MdlTrainResultCorrSettings(),
    var corSettingsTransform: MdlTransformCorrSettings = MdlTransformCorrSettings(),
    var workMode: MdlWorkMode = MdlWorkMode.PROD,
    var stubCase: MdlStubs = MdlStubs.NONE,
    var titleMLModel: MdlTitle = MdlTitle(),

    var requestTitle: MdlRequestTitle = MdlRequestTitle.NONE,
    var timeStart: Instant = Instant.NONE,
    var mlRequest: MdlMl = MdlMl(),
    var mlFilterRequest: MdlMlFilter = MdlMlFilter(),
    var mlAnalyticMl: MdlMlAnalytic = MdlMlAnalytic(),
    var mlTransformMl: MdlMlTransform = MdlMlTransform(),
    var mlTrainResultMl: MdlMlTrainResult= MdlMlTrainResult(),
    var mlModelParameters: MdlMlModelParameters = MdlMlModelParameters(),

    var mlResponse: MdlMl = MdlMl(),
    var mlsResponse: MutableList<MdlMl> = mutableListOf(),
    var mlResponseTrainResult: MdlMlTrainResult = MdlMlTrainResult(),
    var mlsResponseTrainResult: MutableList<MdlMlTrainResult> = mutableListOf(),
    var mlResponseTransform: MdlMlTransform = MdlMlTransform(),
    var mlsResponseTransform: MutableList<MdlMlTransform> = mutableListOf(),

    var mlValidating: MdlMl = MdlMl(),
    var mlAnalyticValidating: MdlMlAnalytic = MdlMlAnalytic(),
    var mlTrainResultValidating: MdlMlTrainResult = MdlMlTrainResult(),
    var mlTransformValidating: MdlMlTransform = MdlMlTransform(),
    var mlResponseTrainModelValidating: MdlMlTrainResult = MdlMlTrainResult(),
    var mlFilterValidating: MdlMlFilter = MdlMlFilter(),

    var mlValidated: MdlMl = MdlMl(),
    var mlValidatedAnalytic: MdlMlAnalytic = MdlMlAnalytic(),
    var mlValidatedTransform: MdlMlTransform = MdlMlTransform(),
    var mlResponseValidatedTrainModel: MdlMlTrainResult = MdlMlTrainResult(),
    var mlFilterValidated: MdlMlFilter = MdlMlFilter(),

    var mlRepo: IRepoMl = IRepoMl.NONE,
    var mlRepoRead: MdlMl = MdlMl(), // То, что прочитали из репозитория
    var mlRepoPrepare: MdlMl = MdlMl(), // То, что готовим для сохранения в БД
    var mlRepoDone: MdlMl = MdlMl(),  // Результат, полученный из БД
    var mlsRepoDone: MutableList<MdlMl> = mutableListOf(),

    var mlRepoTrainResult: IRepoMlTrainResult = IRepoMlTrainResult.NONE,
    var mlRepoTrainResultRead: MdlMlTrainResult = MdlMlTrainResult(), // То, что прочитали из репозитория
    var mlRepoTrainResultPrepare: MdlMlTrainResult = MdlMlTrainResult(), // То, что готовим для сохранения в БД
    var mlRepoTrainResultDone: MdlMlTrainResult = MdlMlTrainResult(),  // Результат, полученный из БД
    var mlsRepoTrainResultDone: MutableList<MdlMlTrainResult> = mutableListOf(),

    var mlRepoTransform: IRepoMlTransform = IRepoMlTransform.NONE,
    var mlRepoTransformRead: MdlMlTransform = MdlMlTransform(), // То, что прочитали из репозитория
    var mlRepoTransformPrepare: MdlMlTransform = MdlMlTransform(), // То, что готовим для сохранения в БД
    var mlRepoTransformDone: MdlMlTransform = MdlMlTransform(),  // Результат, полученный из БД
    var mlsRepoTransformDone: MutableList<MdlMlTransform> = mutableListOf(),

    var mlTrainModel: ITrainModelMl = ITrainModelMl.NONE,
    var mlTrainModelTransform: ITrainModelMlTransform = ITrainModelMlTransform.NONE,
    var mlTrainModelTrainResult: ITrainModelMlTrainResult = ITrainModelMlTrainResult.NONE,
    var mlTrainModelResultDone: MdlMl = MdlMl(),
    var mlTrainModelTrainResultDone: MdlMlTrainResult = MdlMlTrainResult(),
    var mlTrainModelTransformDone: MdlMlTransform = MdlMlTransform()
    )

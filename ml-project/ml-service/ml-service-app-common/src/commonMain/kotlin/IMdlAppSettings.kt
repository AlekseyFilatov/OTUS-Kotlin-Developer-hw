package api.kotlinproject.app.common

import api.kotlinproject.biz.MdlMlAnalyticProcessor
import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.biz.MdlMlTransformProcessor
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.common.MdlTrainResultCorrSettings
import api.kotlinproject.common.MdlTransformCorrSettings

interface IMdlAppSettings {
    val corSettings: MdlCorSettings
    val processor: MdlMlProcessor
    val corSettingsTrainResult: MdlTrainResultCorrSettings
    val processorAnalytic: MdlMlAnalyticProcessor
    val corSettingsTransform: MdlTransformCorrSettings
    val processorTransform: MdlMlTransformProcessor
}

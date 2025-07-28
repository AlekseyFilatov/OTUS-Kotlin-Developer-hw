package api.kotlinproject.app.ktor

import api.kotlinproject.app.common.IMdlAppSettings
import api.kotlinproject.biz.MdlMlAnalyticProcessor
import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.biz.MdlMlTransformProcessor
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.common.MdlTrainResultCorrSettings
import api.kotlinproject.common.MdlTransformCorrSettings

data class MdlAppSettings(
    val appUrls: List<String> = emptyList(),
    override val corSettings: MdlCorSettings = MdlCorSettings(),
    override val processor: MdlMlProcessor = MdlMlProcessor(corSettings),
    override val corSettingsTrainResult: MdlTrainResultCorrSettings = MdlTrainResultCorrSettings(),
    override val processorAnalytic: MdlMlAnalyticProcessor =  MdlMlAnalyticProcessor(corSettingsTrainResult),
    override val corSettingsTransform: MdlTransformCorrSettings = MdlTransformCorrSettings(),
    override val processorTransform: MdlMlTransformProcessor = MdlMlTransformProcessor(corSettingsTransform)
): IMdlAppSettings

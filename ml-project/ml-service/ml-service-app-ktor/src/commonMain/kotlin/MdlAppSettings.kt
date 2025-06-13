package api.kotlinproject.app.ktor

import api.kotlinproject.app.common.IMdlAppSettings
import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlCorSettings

data class MdlAppSettings(
    val appUrls: List<String> = emptyList(),
    override val corSettings: MdlCorSettings = MdlCorSettings(),
    override val processor: MdlMlProcessor = MdlMlProcessor(corSettings),
): IMdlAppSettings

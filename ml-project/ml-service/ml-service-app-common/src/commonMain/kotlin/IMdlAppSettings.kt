package api.kotlinproject.app.common

import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlCorSettings

interface IMdlAppSettings {
    val processor: MdlMlProcessor
    val corSettings: MdlCorSettings
}

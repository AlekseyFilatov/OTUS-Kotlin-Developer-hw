package api.kotlinproject.biz.validation

import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlCorSettings
import api.kotlinproject.common.models.MdlCommand

abstract class BaseBizValidationTest {
    protected abstract val command: MdlCommand
    private val settings by lazy { MdlCorSettings() }
    protected val processor by lazy { MdlMlProcessor(settings) }
}

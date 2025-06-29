package api.kotlinproject.biz.validation

import api.kotlinproject.common.models.MdlCommand
import kotlin.test.Test

class BizValidationReadTest: BaseBizValidationTest() {
    override val command = MdlCommand.READ

    @Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun trimTitle() = validationTitleTrim(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)

}

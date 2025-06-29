package api.kotlinproject.biz.validation

import api.kotlinproject.common.models.MdlCommand
import kotlin.test.Test

class BizValidationDeleteTest: BaseBizValidationTest() {
    override val command = MdlCommand.DELETE

    @Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun trimTitle() = validationTitleTrim(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)



}

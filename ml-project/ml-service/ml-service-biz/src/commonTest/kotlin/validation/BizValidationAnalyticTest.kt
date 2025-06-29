package api.kotlinproject.biz.validation

import api.kotlinproject.common.models.MdlCommand
import kotlin.test.Test

class BizValidationAnalyticTest: BaseBizValidationTest() {
    override val command: MdlCommand = MdlCommand.ANALITYCML

    @Test fun correctFields() = validationFieldsCorrect(command, processor)
    @Test fun emptyFields() = validationFieldsEmpty(command, processor)

}
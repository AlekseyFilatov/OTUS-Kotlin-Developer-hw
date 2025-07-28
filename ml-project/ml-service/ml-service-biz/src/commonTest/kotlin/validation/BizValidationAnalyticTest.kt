package api.kotlinproject.biz.validation

import api.kotlinproject.common.models.MdlCommand
import kotlin.test.Test

class BizValidationAnalyticTest: BaseBizValidationAnalyticTest() {
    override val command: MdlCommand = MdlCommand.ANALITYCML

    @Test fun correctFields() = validationFieldsCorrect(command, processorAnalytic)
    @Test fun emptyFields() = validationFieldsEmpty(command, processorAnalytic)

}
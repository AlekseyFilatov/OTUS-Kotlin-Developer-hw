package api.kotlinproject.biz.validation

import api.kotlinproject.common.models.MdlCommand
import kotlin.test.Test

class BizValidationTransformTest: BaseBizValidationTest() {
    override val command: MdlCommand = MdlCommand.TRANSFORMML

    @Test fun correctTransformFields() = validationTransformFieldsCorrect(command, processor)
    @Test fun emptyTransformFields() = validationTransformFieldsEmpty(command, processor)

}
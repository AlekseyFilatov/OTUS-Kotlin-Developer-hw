package api.kotlinproject.biz.validation

import api.kotlinproject.common.models.MdlCommand
import kotlin.test.Test

class BizValidationTransformTest: BaseBizValidationTransformTest() {
    override val command: MdlCommand = MdlCommand.TRANSFORMML

    @Test fun correctTransformFields() = validationTransformFieldsCorrect(command, processorTransform)
    @Test fun emptyTransformFields() = validationTransformFieldsEmpty(command, processorTransform)

}
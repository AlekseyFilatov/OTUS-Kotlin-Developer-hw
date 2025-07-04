package api.kotlinproject.biz.validation

import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.stubs.MdlMlTransformStub
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = MdlMlTransformStub.get()

fun validationTransformFieldsCorrect(command: MdlCommand, processor: MdlMlProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlTransformMl = MdlMlTransform(
            ticker = stub.ticker,
            taskNumber = stub.taskNumber,
            dateStart = stub.dateStart,
            dateEnd = stub.dateEnd,
            dateOffset = stub.dateOffset,
            batchSize = stub.batchSize
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MdlState.FAILING, ctx.state)
    assertEquals("NVDA", ctx.mlTransformValidating.ticker)
}

fun validationTransformFieldsEmpty(command: MdlCommand, processor: MdlMlProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlTransformMl = MdlMlTransform(
        )
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MdlState.FAILING, ctx.state)
    assertEquals("", ctx.mlTransformValidating.taskNumber)
}
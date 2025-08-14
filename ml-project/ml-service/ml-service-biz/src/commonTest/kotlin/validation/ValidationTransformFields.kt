package api.kotlinproject.biz.validation

import api.kotlinproject.biz.MdlMlTransformProcessor
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.*
import api.kotlinproject.stubs.MdlMlTransformStub
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = MdlMlTransformStub.get()

fun validationTransformFieldsCorrect(command: MdlCommand, processor: MdlMlTransformProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlTransformMl = MdlMlTransform(
            id = MdlMlId("1"),
            ticker = stub.ticker,
            taskNumber = stub.taskNumber,
            dateStart = stub.dateStart,
            dateEnd = stub.dateEnd,
            dateOffset = stub.dateOffset,
            batchSize = stub.batchSize,
            title = stub.title
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size, ctx.errors.joinToString ( "; " ))
    assertNotEquals(MdlState.FAILING, ctx.state)
    assertEquals("NVDA", ctx.mlTransformValidating.ticker)
}

fun validationTransformFieldsEmpty(command: MdlCommand, processor: MdlMlTransformProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlTransformMl = MdlMlTransform(
        )
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size, ctx.errors.joinToString ( "; " ))
    assertEquals(MdlState.FAILING, ctx.state)
    assertEquals("", ctx.mlTransformValidating.taskNumber)
}
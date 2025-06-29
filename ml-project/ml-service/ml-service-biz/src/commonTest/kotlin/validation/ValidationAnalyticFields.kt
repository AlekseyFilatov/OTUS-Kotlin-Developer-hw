package api.kotlinproject.biz.validation

import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.*
import api.kotlinproject.stubs.MdlMlAnalyticStub
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = MdlMlAnalyticStub.get()

fun validationFieldsCorrect(command: MdlCommand, processor: MdlMlProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlAnalyticMl = MdlMlAnalytic(
            ticker = stub.ticker,
            taskNumber = stub.taskNumber,
            dateStart = stub.dateStart,
            dateEnd = stub.dateEnd,
            evalPivotPoint = stub.evalPivotPoint,
            modelParameters = stub.modelParameters,
            dateOffset = stub.dateOffset,
            batchSize = stub.batchSize
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MdlState.FAILING, ctx.state)
    assertEquals(MdlMlTicker("NVDA"), ctx.mlAnalyticValidating.ticker)
}

fun validationFieldsEmpty(command: MdlCommand, processor: MdlMlProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlAnalyticMl = MdlMlAnalytic(
            ticker = stub.ticker,
            taskNumber = MdlMlTaskNumber(""),
            dateStart = stub.dateStart,
            dateEnd = stub.dateEnd,
            evalPivotPoint = stub.evalPivotPoint,
            modelParameters = stub.modelParameters,
            dateOffset = stub.dateOffset,
            batchSize = stub.batchSize
        ),
    )
    processor.exec(ctx)
    assertEquals(2, ctx.errors.size)
    assertEquals(MdlState.FAILING, ctx.state)
    assertEquals(MdlMlTaskNumber(""), ctx.mlAnalyticValidating.taskNumber)
}

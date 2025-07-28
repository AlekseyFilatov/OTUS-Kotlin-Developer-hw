package api.kotlinproject.biz.validation

import api.kotlinproject.biz.MdlMlAnalyticProcessor
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.*
import api.kotlinproject.stubs.MdlMlTrainResultStub
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = MdlMlTrainResultStub.get()

fun validationFieldsCorrect(command: MdlCommand, processor: MdlMlAnalyticProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlTrainResultMl = MdlMlTrainResult(
            id = MdlMlId("1"),
            dateTime = stub.dateTime,
            close = stub.close,
            labelDatetime = stub.labelDatetime,
            realResult = stub.realResult,
            prediction = stub.prediction,
            error = stub.error
        )
        /*MdlMlAnalytic(
            ticker = stub.ticker,
            taskNumber = stub.taskNumber,
            dateStart = stub.dateStart,
            dateEnd = stub.dateEnd,
            evalPivotPoint = stub.evalPivotPoint,
            modelParameters = stub.modelParameters,
            dateOffset = stub.dateOffset,
            batchSize = stub.batchSize,
            id = stub.id
        ),*/
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size, ctx.errors.joinToString ( "; " ))

    assertNotEquals(MdlState.FAILING, ctx.state)
    assertEquals(MdlMlId("1"), ctx.mlTrainResultValidating.id)
}

fun validationFieldsEmpty(command: MdlCommand, processor: MdlMlAnalyticProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlTrainResultMl = MdlMlTrainResult(
            id = MdlMlId("1"),
            dateTime = stub.dateTime,
            close = stub.close,
            labelDatetime = stub.labelDatetime,
            realResult = stub.realResult,
            prediction = stub.prediction,
            error = stub.error
        )
        /*mlAnalyticMl = MdlMlAnalytic(
            ticker = stub.ticker,
            taskNumber = MdlMlTaskNumber(""),
            dateStart = stub.dateStart,
            dateEnd = stub.dateEnd,
            evalPivotPoint = stub.evalPivotPoint,
            modelParameters = stub.modelParameters,
            dateOffset = stub.dateOffset,
            batchSize = stub.batchSize
        ),*/
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size, ctx.errors.joinToString ( "; " ))
    assertNotEquals(MdlState.FAILING, ctx.state)
    assertEquals(MdlMlId("1"), ctx.mlTrainResultValidating.id)
}

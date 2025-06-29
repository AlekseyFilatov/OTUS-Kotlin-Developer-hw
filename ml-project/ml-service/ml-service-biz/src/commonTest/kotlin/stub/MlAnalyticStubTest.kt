package api.kotlinproject.biz.stub

import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.*
import api.kotlinproject.common.stubs.MdlStubs
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MlAnalyticStubTest {

    private val processor = MdlMlProcessor()

    val ticker = MdlMlTicker("NVDA")
    val taskNumber = MdlMlTaskNumber("123")
    val dateStart = "1900-01-01"
    val dateEnd = "2025-01-01"
    val evalPivotPoint  = 10.toLong()
    val dateOffset = 2.toLong()
    val modelParameters = MdlMlModelParameters(
        learningRate = 0.05,
        maxDepth = 8,
        subSample = 0.8,
        gamma = 1,
        numRound = 500,
        treeMethod = "gpu_hist",
        refreshLeaf = 1,
        processType = "default"
    )
    val batchSize = 1.toLong()

    val dateTime = "1900-01-01"
    val close = 1.0
    val labelDatetime = "1900-01-01"
    val realResult = 2.0
    val prediction = 2.0
    val error = 0.0


    @Test
    fun createAnalyticSuccess() = runTest {

        val ctx = MdlContext(
            command = MdlCommand.ANALITYCML,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.SUCCESS,
            mlAnalyticMl = MdlMlAnalytic(
                ticker = ticker,
                taskNumber = taskNumber,
                dateStart = dateStart,
                dateEnd = dateEnd,
                evalPivotPoint = evalPivotPoint,
                modelParameters = modelParameters,
                dateOffset = dateOffset,
                batchSize = batchSize
            ),
            mlResponseTrainModel = MdlMlTrainResult(
                dateTime = dateTime,
                close = close,
                labelDatetime = labelDatetime,
                realResult = realResult,
                prediction = prediction,
                error = error
            )
        )
        processor.exec(ctx)
        assertEquals(taskNumber, ctx.mlAnalyticMl.taskNumber)
        assertEquals(realResult, ctx.mlResponseTrainModel.realResult)
    }

    @Test
    fun badAnalytic() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.ANALITYCML,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.BAD_ANALYTIC,
            mlAnalyticMl = MdlMlAnalytic(
            ),
            mlResponseTrainModel = MdlMlTrainResult(
            ),
        )
        processor.exec(ctx)
        assertEquals(MdlMlAnalytic(), ctx.mlAnalyticMl)
        assertEquals("analytic", ctx.errors.firstOrNull()?.field)
        assertEquals("validation-analytic", ctx.errors.firstOrNull()?.code)
    }
    @Test
    fun databaseError() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.CREATE,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.DB_ERROR,
            mlAnalyticMl = MdlMlAnalytic(
            ),
            mlResponseTrainModel = MdlMlTrainResult(
            )
        )
        processor.exec(ctx)
        assertEquals(MdlMlAnalytic(), ctx.mlAnalyticMl)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = MdlContext(
            command = MdlCommand.CREATE,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.NONE,
            mlAnalyticMl = MdlMlAnalytic(
            ),
            mlResponseTrainModel = MdlMlTrainResult(
            )
        )
        processor.exec(ctx)
        assertEquals(MdlMlAnalytic(), ctx.mlAnalyticMl)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}

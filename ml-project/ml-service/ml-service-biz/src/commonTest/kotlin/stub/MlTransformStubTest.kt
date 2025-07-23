package api.kotlinproject.biz.stub

import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.biz.MdlMlTransformProcessor
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.common.models.MdlMlTransform
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.common.stubs.MdlStubs
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MlTransformStubTest {

    private val processor = MdlMlProcessor()
    private val processorTransform = MdlMlTransformProcessor()

    val ticker = "NVDA"
    val taskNumber = "123"
    val dateStart = "1900-01-01"
    val dateEnd = "2025-01-01"
    val dateOffset = 2.toLong()
    val batchSize = 1

    @Test
    fun createTransformSuccess() = runTest {

        val ctx = MdlContext(
            command = MdlCommand.TRANSFORMML,
            state = MdlState.NONE,
            workMode = MdlWorkMode.STUB,
            stubCase = MdlStubs.SUCCESS,
            mlTransformMl = MdlMlTransform(
                ticker = ticker,
                taskNumber = taskNumber,
                dateStart = dateStart,
                dateEnd = dateEnd,
                dateOffset = dateOffset,
                batchSize = batchSize
            ),
            mlResponseTransform = MdlMlTransform(
                ticker = ticker,
                taskNumber = taskNumber,
                dateStart = dateStart,
                dateEnd = dateEnd,
                dateOffset = dateOffset,
                batchSize = batchSize
            )
        )
        processorTransform.exec(ctx)

        assertEquals(taskNumber, ctx.mlTransformMl.taskNumber)
        assertEquals(ticker, ctx.mlResponseTransform.ticker)
    }

      @Test
      fun badTransform() = runTest {
          val ctx = MdlContext(
              command = MdlCommand.TRANSFORMML,
              state = MdlState.NONE,
              workMode = MdlWorkMode.STUB,
              stubCase = MdlStubs.BAD_TRANSFORM,
              mlTransformMl = MdlMlTransform(
              ),
              mlResponseTransform = MdlMlTransform(
              ),
          )
          processorTransform.exec(ctx)
          assertEquals(MdlMlTransform(), ctx.mlTransformMl)
          assertEquals("transform", ctx.errors.firstOrNull()?.field)
          assertEquals("validation", ctx.errors.firstOrNull()?.group)
      }

      @Test
      fun databaseError() = runTest {
          val ctx = MdlContext(
              command = MdlCommand.TRANSFORMML,
              state = MdlState.NONE,
              workMode = MdlWorkMode.STUB,
              stubCase = MdlStubs.DB_ERROR,
              mlTransformMl = MdlMlTransform(
              ),
              mlResponseTransform = MdlMlTransform(
              ),
          )
          processorTransform.exec(ctx)
          assertEquals(MdlMlTransform(), ctx.mlTransformMl)
          assertEquals("internal", ctx.errors.firstOrNull()?.group)
      }

      @Test
      fun badNoCase() = runTest {
          val ctx = MdlContext(
              command = MdlCommand.TRANSFORMML,
              state = MdlState.NONE,
              workMode = MdlWorkMode.STUB,
              stubCase = MdlStubs.BAD_TITLE,
              mlTransformMl = MdlMlTransform(
              ),
              mlResponseTransform = MdlMlTransform(
              ),
          )
          processorTransform.exec(ctx)
          assertEquals(MdlMlTransform(), ctx.mlTransformMl)
          assertEquals("stub", ctx.errors.firstOrNull()?.field)
          assertEquals("validation", ctx.errors.firstOrNull()?.group)
      }
}

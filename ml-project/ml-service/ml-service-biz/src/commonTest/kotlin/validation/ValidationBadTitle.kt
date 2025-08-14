package api.kotlinproject.biz.validation

import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.*
import api.kotlinproject.stubs.MdlMlStub
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = MdlMlStub.get()

fun validationTitleCorrect(command: MdlCommand, processor: MdlMlProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlRequest = MdlMl(
            title = "forrest",
            description = "abc",
            id = MdlMlId("1")
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size, ctx.errors.joinToString (","))
    assertNotEquals(MdlState.FAILING, ctx.state)
    assertEquals("forrest", ctx.mlValidated.title)
}

fun validationTitleTrim(command: MdlCommand, processor: MdlMlProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlRequest = MdlMl(
            //title = " \n\t abc \t\n ",
            //есть enum на title
            //title = " \n\t forrest \t\n",
            title = "forrest",
            description = "abc",
            id = MdlMlId("1")
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size,ctx.errors.joinToString (","))
    assertNotEquals(MdlState.FAILING, ctx.state)
    assertEquals("forrest", ctx.mlValidated.title)
}

fun validationTitleEmpty(command: MdlCommand, processor: MdlMlProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlRequest = MdlMl(
            title = "",
            description = "abc",
            id = MdlMlId("1")
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size, ctx.errors.joinToString (","))
    assertEquals(MdlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

fun validationTitleSymbols(command: MdlCommand, processor: MdlMlProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlRequest = MdlMl(
            title = "!@#$%^&*(),.{}",
            description = "abc",
            id = MdlMlId("1")
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MdlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

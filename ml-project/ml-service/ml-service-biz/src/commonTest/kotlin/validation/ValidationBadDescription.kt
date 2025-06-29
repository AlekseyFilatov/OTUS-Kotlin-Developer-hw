package api.kotlinproject.biz.validation

import api.kotlinproject.biz.MdlMlProcessor
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlState
import api.kotlinproject.common.models.MdlWorkMode
import api.kotlinproject.stubs.MdlMlStub
import kotlinx.coroutines.test.runTest
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = MdlMlStub.get()

fun validationDescriptionCorrect(command: MdlCommand, processor: MdlMlProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlRequest = MdlMl(
            title = stub.title,
            description = "abc",
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MdlState.FAILING, ctx.state)
    assertEquals("abc", ctx.mlValidated.description)
}

fun validationDescriptionTrim(command: MdlCommand, processor: MdlMlProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlRequest = MdlMl(
            title = stub.title,
            description = " \n\tabc \n\t",
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(MdlState.FAILING, ctx.state)
    assertEquals("abc", ctx.mlValidated.description)
}

fun validationDescriptionEmpty(command: MdlCommand, processor: MdlMlProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlRequest = MdlMl(
            title = stub.title,
            description = "",
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MdlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

fun validationDescriptionSymbols(command: MdlCommand, processor: MdlMlProcessor) = runTest {
    val ctx = MdlContext(
        command = command,
        state = MdlState.NONE,
        workMode = MdlWorkMode.TEST,
        mlRequest = MdlMl(
            title = stub.title,
            description = "!@#$%^&*(),.{}",
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(MdlState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

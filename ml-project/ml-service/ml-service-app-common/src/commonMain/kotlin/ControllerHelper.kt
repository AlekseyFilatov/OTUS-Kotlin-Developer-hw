package api.kotlinproject.app.common

import api.kotlinproject.api.log1.mapper.toLog
import api.kotlinproject.common.MdlContext
import api.kotlinproject.common.helpers.asMdlError
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.common.models.MdlState
import kotlinx.datetime.Clock
import kotlin.reflect.KClass

suspend inline fun <T> IMdlAppSettings.controllerHelper(
    crossinline getRequest: suspend MdlContext.() -> Unit,
    crossinline toResponse: suspend MdlContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = MdlContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId started for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId processed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId failed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId),
            e = e
        )
        ctx.state = MdlState.FAILING
        ctx.errors.add(e.asMdlError())
        processor.exec(ctx)
        if (ctx.command == MdlCommand.NONE) {
            ctx.command = MdlCommand.READ
        }
        ctx.toResponse()
    }
}

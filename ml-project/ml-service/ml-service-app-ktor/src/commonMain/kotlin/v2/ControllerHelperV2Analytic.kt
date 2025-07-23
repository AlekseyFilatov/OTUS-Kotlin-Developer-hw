package api.kotlinproject.app.ktor.v2

import api.kotlinproject.api.v1.models.IRequest
import api.kotlinproject.api.v1.models.IResponse
import api.kotlinproject.app.common.controllerHelperAnalytic
import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.mappers.kmpmappers.fromTransport
import api.kotlinproject.mappers.kmpmappers.toTransportMl
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV2Analytic(
    appSettings: MdlAppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelperAnalytic(
    {
        fromTransport(this@processV2Analytic.receive<Q>())
    },
    { this@processV2Analytic.respond(toTransportMl() as R) },
    clazz,
    logId,
)

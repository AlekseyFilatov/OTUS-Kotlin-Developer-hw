package api.kotlinproject.app.ktor.v1

import api.kotlinproject.api.jackson.v1.models.IRequest
import api.kotlinproject.api.jackson.v1.models.IResponse
import api.kotlinproject.app.common.controllerHelperAnalytic
import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.mappers.v1.jacksonmappers.fromTransport
import api.kotlinproject.mappers.v1.jacksonmappers.toTransportMl
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV1Analytic(
    appSettings: MdlAppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelperAnalytic(
    {
        fromTransport(this@processV1Analytic.receive<Q>())
    },
    { this@processV1Analytic.respond(toTransportMl() as R) },
    clazz,
    logId,
)

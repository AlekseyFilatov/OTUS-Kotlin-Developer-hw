package api.kotlinproject.app.ktor.v1

import api.kotlinproject.api.jackson.v1.models.IRequest
import api.kotlinproject.api.jackson.v1.models.IResponse
import api.kotlinproject.app.common.controllerHelper
import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.mappers.v1.jacksonmappers.fromTransport
import api.kotlinproject.mappers.v1.jacksonmappers.toTransportMl
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV1(
    appSettings: MdlAppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelper(
    {
        fromTransport(this@processV1.receive<Q>())
    },
    { this@processV1.respond(toTransportMl() as R) },
    clazz,
    logId,
)

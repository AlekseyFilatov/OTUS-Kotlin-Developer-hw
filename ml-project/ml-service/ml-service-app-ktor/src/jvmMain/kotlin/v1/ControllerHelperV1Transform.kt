package api.kotlinproject.app.ktor.v1


import api.kotlinproject.api.jackson.v1.models.IRequest
import api.kotlinproject.api.jackson.v1.models.IResponse
import api.kotlinproject.app.common.controllerHelperTransform
import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.mappers.v1.jacksonmappers.fromTransport
import api.kotlinproject.mappers.v1.jacksonmappers.toTransportMl
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV1Transform(
    appSettings: MdlAppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelperTransform(
    {
        fromTransport(this@processV1Transform.receive<Q>())
    },
    { this@processV1Transform.respond(toTransportMl() as R) },
    clazz,
    logId,
)

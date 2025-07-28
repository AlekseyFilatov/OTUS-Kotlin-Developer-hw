package api.kotlinproject.app.ktor.v2

import api.kotlinproject.app.common.controllerHelperTransform

import api.kotlinproject.api.v1.models.IRequest
import api.kotlinproject.api.v1.models.IResponse

import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.mappers.kmpmappers.fromTransport
import api.kotlinproject.mappers.kmpmappers.toTransportMl
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV2Transform(
    appSettings: MdlAppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelperTransform(
    {
        fromTransport(this@processV2Transform.receive<Q>())
    },
    { this@processV2Transform.respond(toTransportMl() as R) },
    clazz,
    logId,
)

package api.kotlinproject.app.ktor.v2

import api.kotlinproject.api.v1.models.*
import api.kotlinproject.app.ktor.MdlAppSettings
import io.ktor.server.application.*
import kotlin.reflect.KClass

val clCreate: KClass<*> = ApplicationCall::createMl::class
suspend fun ApplicationCall.createMl(appSettings: MdlAppSettings) =
    processV2<MlCreateRequest, MlCreateResponse>(appSettings, clCreate,"create")

val clRead: KClass<*> = ApplicationCall::readMl::class
suspend fun ApplicationCall.readMl(appSettings: MdlAppSettings) =
    processV2<MlReadRequest, MlReadResponse>(appSettings, clRead, "read")

val clUpdate: KClass<*> = ApplicationCall::updateMl::class
suspend fun ApplicationCall.updateMl(appSettings: MdlAppSettings) =
    processV2<MlUpdateRequest, MlUpdateResponse>(appSettings, clUpdate, "update")

val clDelete: KClass<*> = ApplicationCall::deleteMl::class
suspend fun ApplicationCall.deleteMl(appSettings: MdlAppSettings) =
    processV2<MlDeleteRequest, MlDeleteResponse>(appSettings, clDelete, "delete")

val clSearch: KClass<*> = ApplicationCall::searchMl::class
suspend fun ApplicationCall.searchMl(appSettings: MdlAppSettings) =
    processV2<MlSearchRequest, MlSearchResponse>(appSettings, clSearch, "search")

val clAnalyticMl: KClass<*> = ApplicationCall::analyticMl::class
suspend fun ApplicationCall.analyticMl(appSettings: MdlAppSettings) =
    processV2<AnalyticMlReadRequest, AnalyticMlReadResponse>(appSettings, clAnalyticMl, "analytic")

val clTransformMl: KClass<*> = ApplicationCall::transformMl::class
suspend fun ApplicationCall.transformMl(appSettings: MdlAppSettings) =
    processV2<TransformMlUpdateRequest, TransformMlUpdateResponse>(appSettings, clTransformMl, "transform")


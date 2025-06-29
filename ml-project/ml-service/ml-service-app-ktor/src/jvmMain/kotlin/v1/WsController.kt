package api.kotlinproject.app.ktor.v1

import api.kotlinproject.api.jackson.v1.apiV1Mapper
import api.kotlinproject.api.jackson.v1.models.IRequest
import api.kotlinproject.app.common.controllerHelper
import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.app.ktor.base.KtorWsSessionV1
import api.kotlinproject.common.models.MdlCommand
import api.kotlinproject.mappers.v1.jacksonmappers.fromTransport
import api.kotlinproject.mappers.v1.jacksonmappers.toTransportInit
import api.kotlinproject.mappers.v1.jacksonmappers.toTransportMl
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.reflect.KClass

private val clWsV1: KClass<*> = WebSocketSession::wsHandlerV1::class
suspend fun WebSocketSession.wsHandlerV1(appSettings: MdlAppSettings) = with(KtorWsSessionV1(this)) {
    val sessions = appSettings.corSettings.wsSessions
    sessions.add(this)

    // Handle init request
    appSettings.controllerHelper(
        {
            command = MdlCommand.INIT
            wsSession = this@with
        },
        { outgoing.send(Frame.Text(apiV1Mapper.writeValueAsString(toTransportInit()))) },
        clWsV1,
        "wsV1-init"
    )

    // Handle flow
    incoming.receiveAsFlow().mapNotNull {
        val frame = it as? Frame.Text ?: return@mapNotNull
        // Handle without flow destruction
        try {
            appSettings.controllerHelper(
                {
                    fromTransport(apiV1Mapper.readValue<IRequest>(frame.readText()))
                    wsSession = this@with
                },
                {
                    val result = apiV1Mapper.writeValueAsString(toTransportMl())
                    // If change request, response is sent to everyone
                    outgoing.send(Frame.Text(result))
                },
                clWsV1,
                "wsV1-handle"
            )

        } catch (_: ClosedReceiveChannelException) {
            sessions.remove(this@with)
        } finally {
            // Handle finish request
            appSettings.controllerHelper(
                {
                    command = MdlCommand.FINISH
                    wsSession = this@with
                },
                { },
                clWsV1,
                "wsV1-finish"
            )
            sessions.remove(this@with)
        }
    }.collect()
}
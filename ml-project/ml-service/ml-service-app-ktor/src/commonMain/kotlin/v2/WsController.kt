package api.kotlinproject.app.ktor.v2

import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.receiveAsFlow
import api.kotlinproject.api.v1.apiV2RequestDeserialize
import api.kotlinproject.api.v1.apiV2ResponseSerialize
import api.kotlinproject.mappers.kmpmappers.fromTransport
import api.kotlinproject.mappers.kmpmappers.toTransportMl
import api.kotlinproject.mappers.kmpmappers.toTransportInit
import api.kotlinproject.api.v1.models.IRequest
import api.kotlinproject.app.common.controllerHelper
import api.kotlinproject.app.ktor.MdlAppSettings
import api.kotlinproject.app.ktor.base.KtorWsSessionV2
import api.kotlinproject.common.models.MdlCommand
import kotlin.reflect.KClass

private val clWsV2: KClass<*> = WebSocketSession::wsHandlerV2::class
suspend fun WebSocketSession.wsHandlerV2(appSettings: MdlAppSettings) = with(KtorWsSessionV2(this)) {
    // Обновление реестра сессий
    val sessions = appSettings.corSettings.wsSessions
    sessions.add(this)

    // Handle init request
    appSettings.controllerHelper(
        {
            command = MdlCommand.INIT
            wsSession = this@with
        },
        { outgoing.send(Frame.Text(apiV2ResponseSerialize(toTransportInit()))) },
        clWsV2,
        "wsV2-init"
    )

    // Handle flow
    incoming.receiveAsFlow()
        .mapNotNull { it ->
            val frame = it as? Frame.Text ?: return@mapNotNull
            // Handle without flow destruction
            try {
                appSettings.controllerHelper(
                    {
                        fromTransport(apiV2RequestDeserialize<IRequest>(frame.readText()))
                        wsSession = this@with
                    },
                    {
                        val result = apiV2ResponseSerialize(toTransportMl())
                        // If change request, response is sent to everyone
                        outgoing.send(Frame.Text(result))
                    },
                    clWsV2,
                    "wsV2-handle"
                )

            } catch (_: ClosedReceiveChannelException) {
                sessions.remove(this@with)
            } catch (e: Throwable) {
                println("FFF")
            }
        }
        .onCompletion {
            // Handle finish request
            appSettings.controllerHelper(
                {
                    command = MdlCommand.FINISH
                    wsSession = this@with
                },
                { },
                clWsV2,
                "wsV2-finish"
            )
            sessions.remove(this@with)
        }
        .collect()
}
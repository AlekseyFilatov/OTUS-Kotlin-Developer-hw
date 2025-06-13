package api.kotlinproject.app.ktor.base

import api.kotlinproject.api.jackson.v1.apiV1ResponseSerialize
import api.kotlinproject.api.jackson.v1.models.IResponse
import api.kotlinproject.common.ws.IMdlWsSession
import io.ktor.websocket.*

data class KtorWsSessionV1(
    private val session: WebSocketSession
) : IMdlWsSession {
    override suspend fun <T> send(obj: T) {
        require(obj is IResponse)
        session.send(Frame.Text(apiV1ResponseSerialize(obj)))
    }
}
package api.kotlinproject.app.ktor.base

import api.kotlinproject.common.ws.IMdlWsSession
import api.kotlinproject.common.ws.IMdlWsSessionRepo

class KtorWsSessionRepo: IMdlWsSessionRepo {
    private val sessions: MutableSet<IMdlWsSession> = mutableSetOf()
    override fun add(session: IMdlWsSession) {
        sessions.add(session)
    }

    override fun clearAll() {
        sessions.clear()
    }

    override fun remove(session: IMdlWsSession) {
        sessions.remove(session)
    }

    override suspend fun <T> sendAll(obj: T) {
        sessions.forEach { it.send(obj) }
    }
}
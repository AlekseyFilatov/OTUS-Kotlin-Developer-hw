package api.kotlinproject.common.ws

interface IMdlWsSessionRepo {

    fun add(session: IMdlWsSession)
    fun clearAll()
    fun remove(session: IMdlWsSession)
    suspend fun <K> sendAll(obj: K)

    companion object {
        val NONE = object : IMdlWsSessionRepo {
            override fun add(session: IMdlWsSession) {}
            override fun clearAll() {}
            override fun remove(session: IMdlWsSession) {}
            override suspend fun <K> sendAll(obj: K) {}
        }
    }
}
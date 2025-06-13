package api.kotlinproject.common.ws

interface IMdlWsSession {

    suspend fun <T> send(obj: T)
    companion object {
        val NONE = object : IMdlWsSession {
            override suspend fun <T> send(obj: T) {

            }
        }
    }
}
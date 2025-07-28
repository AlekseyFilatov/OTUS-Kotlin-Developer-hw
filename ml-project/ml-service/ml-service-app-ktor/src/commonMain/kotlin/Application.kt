package api.kotlinproject.app.ktor

import api.kotlinproject.api.v1.apiV2Mapper
import api.kotlinproject.app.ktor.plugins.initAppSettings
import api.kotlinproject.app.ktor.v2.v2Ml
import api.kotlinproject.app.ktor.v2.wsHandlerV2
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*

fun Application.module(
    appSettings: MdlAppSettings = initAppSettings()
) {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        /* TODO
            Это временное решение, оно опасно.
            В боевом приложении здесь должны быть конкретные настройки
        */
        anyHost()
    }
    install(WebSockets)

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        route("v2") {
            install(ContentNegotiation) {
                json(apiV2Mapper)
            }
            v2Ml(appSettings)
            webSocket("/ws") {
                wsHandlerV2(appSettings)
            }
        }
    }
}

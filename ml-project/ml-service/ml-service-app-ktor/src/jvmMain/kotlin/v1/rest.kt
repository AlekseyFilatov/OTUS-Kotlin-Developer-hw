package api.kotlinproject.app.ktor.v1

import api.kotlinproject.app.ktor.MdlAppSettings
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.v1Ml(appSettings: MdlAppSettings) {
    route("ml") {
        post("create") {
            call.createMl(appSettings)
        }
        post("read") {
            call.readMl(appSettings)
        }
        post("update") {
            call.updateMl(appSettings)
        }
        post("delete") {
            call.deleteMl(appSettings)
        }
        post("search") {
            call.searchMl(appSettings)
        }
        post("analyticml") {
            call.analyticMl(appSettings)
        }
        post("transformml") {
            call.transformMl(appSettings)
        }
    }
}

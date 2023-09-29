package io.github.elkhoudiry.klogger.route.health

import io.ktor.server.application.call
import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.datetime.Clock
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

fun Routing.health(apiBase: String) {
    get(Regex("$apiBase/health.*")) {
        call.respondRedirect("$apiBase/health")
    }
    get("$apiBase/health") {
        call.respond(
            buildJsonObject {
                put("datetime", Clock.System.now().toString())
                put("method", call.request.httpMethod.value)
                put("status", "OK")
                put("url", call.request.uri)
            }
        )
    }
}
package io.github.elkhoudiry.klogger.route.health

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

fun Routing.health(apiBase: String) {
    get(Regex("$apiBase/health.*")) {
        call.respondRedirect("$apiBase/health")
    }
    get("$apiBase/health") {
        call.respond(HttpStatusCode.OK)
    }
}
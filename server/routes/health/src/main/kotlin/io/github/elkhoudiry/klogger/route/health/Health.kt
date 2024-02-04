package io.github.elkhoudiry.klogger.route.health

import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.datetime.Clock
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

fun Routing.health() {
    get(Regex("/api/v1/health.*")) {
        call.respondRedirect("/api/v1/health")
    }

    get("/api/v1/health") {
        call.respond(call.health())
    }
}

fun ApplicationCall.health(): JsonObject {
    return buildJsonObject {
        put("health", "OK")
        put(
            "datetime",
            Clock.System
                .now()
                .toString()
        )
        put("method", request.httpMethod.value)
        put("uri", request.uri)
    }
}
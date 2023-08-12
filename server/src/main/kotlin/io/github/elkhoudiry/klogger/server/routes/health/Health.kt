package io.github.elkhoudiry.klogger.server.routes.health

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

internal fun Routing.health(apiBase: String) {
    get("$apiBase/health") {
        call.respond(buildJsonObject {
            put("status", "OK")
            put("datetime", Clock.System.now().toString())
        })
    }
}
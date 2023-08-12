package io.github.elkhoudiry.klogger.server.routes.health

import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.ZoneOffset
import kotlinx.datetime.offsetIn
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

internal fun Routing.health(apiBase: String) {
    get(Regex("$apiBase/health.*")) {
        call.respondRedirect("$apiBase/health")
    }
    get("$apiBase/health") {
        call.respond(
            buildJsonObject {
                put("datetime", Clock.System.now().toString())
                put("status", "OK")
            }
        )
    }
}
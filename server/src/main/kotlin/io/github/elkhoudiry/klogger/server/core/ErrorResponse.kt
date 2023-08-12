package io.github.elkhoudiry.klogger.server.core

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

suspend fun ApplicationCall.errorResponse(
    code: Int,
    exception: Throwable,
    message: String?,
    url: String
) {
    respond(
        status = HttpStatusCode.fromValue(code),
        message = buildJsonObject {
            put("datetime", Clock.System.now().toString())
            put("error", exception::class.simpleName)
            put("message", message ?: "No error message provided.")
            put("method", request.httpMethod.value)
            put("url", url)
        },
    )
}
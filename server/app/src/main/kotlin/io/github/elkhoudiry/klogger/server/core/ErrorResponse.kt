package io.github.elkhoudiry.klogger.server.core

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.response.respond
import kotlinx.datetime.Clock
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

suspend fun ApplicationCall.errorResponse(
    code: Int,
    exception: Throwable,
    message: String?
) {
    respond(
        status = HttpStatusCode.fromValue(code),
        message = buildJsonObject {
            put("datetime", Clock.System.now().toString())
            put("error", exception::class.simpleName)
            put("message", message ?: "No error message provided.")
            put("method", request.httpMethod.value)
            put("url", request.uri)
        }
    )
}
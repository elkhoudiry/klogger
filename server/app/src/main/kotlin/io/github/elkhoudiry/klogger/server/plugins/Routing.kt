package io.github.elkhoudiry.klogger.server.plugins

import io.github.elkhoudiry.klogger.core.shared.serialization.json
import io.github.elkhoudiry.klogger.route.health.health
import io.github.elkhoudiry.klogger.route.logs.log
import io.github.elkhoudiry.klogger.server.core.errorResponse
import io.github.elkhoudiry.klogger.server.data.common.models.ResponseException
import io.ktor.content.TextContent
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.response.ApplicationSendPipeline
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.netty.handler.codec.http.HttpResponseStatus
import kotlinx.datetime.Clock
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

fun Application.configureRouting() {
    val apiBase = "/api/v1"

    install(StatusPages) { exception<Throwable> { call, cause -> call.fail(cause) } }
    routing {
        get("/") { call.respondText("Hello World!") }
        health(apiBase)
        log(apiBase)
        sendPipeline.intercept(ApplicationSendPipeline.Transform) {
            proceedWith(it.override(this.context))
        }
    }
}

private suspend fun ApplicationCall.fail(failure: Throwable) = when (failure) {
    is ResponseException -> {
        errorResponse(
            HttpResponseStatus.BAD_REQUEST.code(),
            failure,
            failure.technicalMessage
        )
    }

    else -> {
        errorResponse(
            HttpResponseStatus.BAD_REQUEST.code(),
            failure,
            failure.message
        )
    }
}

private fun Any.override(call: ApplicationCall): Any {
    if (this !is TextContent) return this
    if (call.request.uri.contains("/health")) return this

    val overridden = JsonObject(
        mapOf(
            "datetime" to JsonPrimitive(
                Clock.System
                    .now()
                    .toString()
            ),
            "method" to JsonPrimitive(call.request.httpMethod.value),
            "url" to JsonPrimitive(call.request.uri),
            "payload" to json.parseToJsonElement(this.text)
        )
    )

    return TextContent(json.encodeToString(JsonElement.serializer(), overridden), this.contentType, this.status)
}
package io.github.elkhoudiry.klogger.server.plugins

import io.github.elkhoudiry.klogger.route.health.health
import io.github.elkhoudiry.klogger.route.logs.log
import io.github.elkhoudiry.klogger.server.core.errorResponse
import io.github.elkhoudiry.klogger.server.data.common.models.ResourceNotFoundException
import io.github.elkhoudiry.klogger.server.data.common.models.ResponseException
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.netty.handler.codec.http.HttpResponseStatus

fun Application.configureRouting() {
    val apiBase = "/api/v1"

    install(StatusPages) { exception<Throwable> { call, cause -> call.fail(cause) } }
    routing {
        get("/") { call.respondText("Hello World!") }
        health(apiBase)
        log(apiBase)
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

    is ResourceNotFoundException -> {
        errorResponse(
            HttpResponseStatus.NOT_FOUND.code(),
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
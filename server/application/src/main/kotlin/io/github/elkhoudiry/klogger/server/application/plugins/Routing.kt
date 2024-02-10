package io.github.elkhoudiry.klogger.server.application.plugins

import io.github.elkhoudiry.klogger.server.application.core.errorResponse
import io.github.elkhoudiry.klogger.server.data.common.models.ResourceNotFoundException
import io.github.elkhoudiry.klogger.server.data.common.models.ResponseException
import io.github.elkhoudiry.klogger.server.data.database.common.logs.LogsLocalCache
import io.github.elkhoudiry.klogger.server.data.logs.repositories.DefaultLoggerRepository
import io.github.elkhoudiry.klogger.server.route.events.events
import io.github.elkhoudiry.klogger.server.route.health.health
import io.github.elkhoudiry.klogger.server.route.logs.logs
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.netty.handler.codec.http.HttpResponseStatus

fun Application.configureRouting(
    logsLocalCache: Lazy<LogsLocalCache>
) {
    install(StatusPages) { exception<Throwable> { call, cause -> call.fail(cause) } }
    routing {
        get("/") { call.respondText("Hello Klogger!") }
        events()
        health()
        logs(DefaultLoggerRepository(logsLocalCache))
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
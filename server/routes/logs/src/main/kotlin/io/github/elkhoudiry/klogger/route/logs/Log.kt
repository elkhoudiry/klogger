package io.github.elkhoudiry.klogger.route.logs

import io.github.elkhoudiry.klogger.route.logs.usecases.LogRouteUseCases
import io.github.elkhoudiry.server.data.logger.repositories.DefaultLoggerRepository
import io.github.elkhoudiry.server.data.logger.repositories.LoggerRepository
import io.ktor.server.application.call
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post

private val logger: LoggerRepository = DefaultLoggerRepository()
private val useCases = LogRouteUseCases(logging = logger)

fun Routing.log(apiBase: String) {
    get(Regex("$apiBase/logs/?")) {
        useCases.getAll.execute(call)
    }

    post(Regex("$apiBase/logs/?")) {
        useCases.insertLog.execute(call)
    }

    get("$apiBase/logs/{id}/") {
        call.respondRedirect("$apiBase/logs/${call.parameters["id"]}", true)
    }

    get("$apiBase/logs/{id}") {
        useCases.getById.execute(call)
    }

    delete("$apiBase/logs/{id}") {
        useCases.deleteById.execute(call)
    }
}
package io.github.elkhoudiry.klogger.server.route.logs

import io.github.elkhoudiry.klogger.server.data.logs.repositories.DefaultLoggerRepository
import io.github.elkhoudiry.klogger.server.data.logs.repositories.LoggerRepository
import io.github.elkhoudiry.klogger.server.route.common.deleteInclusive
import io.github.elkhoudiry.klogger.server.route.common.getInclusive
import io.github.elkhoudiry.klogger.server.route.common.postInclusive
import io.github.elkhoudiry.klogger.server.route.logs.usecases.LogsRouteUseCases
import io.ktor.server.application.call
import io.ktor.server.routing.Routing

private val logger: LoggerRepository = DefaultLoggerRepository()
private val useCases = LogsRouteUseCases(logging = logger)

fun Routing.logs() {
    getInclusive("/api/v1/logs") { useCases.getAll.execute(call) }

    postInclusive("/api/v1/logs") { useCases.insertLog.execute(call) }

    getInclusive("/api/v1/logs/{id}") { useCases.getById.execute(call) }

    deleteInclusive("/api/v1/logs/{id}") { useCases.deleteById.execute(call) }
}
package io.github.elkhoudiry.klogger.route.logs

import io.github.elkhoudiry.klogger.route.common.deleteInclusive
import io.github.elkhoudiry.klogger.route.common.getInclusive
import io.github.elkhoudiry.klogger.route.common.postInclusive
import io.github.elkhoudiry.klogger.route.logs.usecases.LogRouteUseCases
import io.github.elkhoudiry.server.data.logger.repositories.DefaultLoggerRepository
import io.github.elkhoudiry.server.data.logger.repositories.LoggerRepository
import io.ktor.server.application.call
import io.ktor.server.routing.Routing

private val logger: LoggerRepository = DefaultLoggerRepository()
private val useCases = LogRouteUseCases(logging = logger)

fun Routing.log() {
    getInclusive("/api/v1/logs") { useCases.getAll.execute(call) }

    postInclusive("/api/v1/logs") { useCases.insertLog.execute(call) }

    getInclusive("/api/v1/logs/{id}") { useCases.getById.execute(call) }

    deleteInclusive("/api/v1/logs/{id}") { useCases.deleteById.execute(call) }
}
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

fun Routing.log(apiBase: String) {
    getInclusive("$apiBase/logs") { useCases.getAll.execute(call) }

    postInclusive("$apiBase/logs") { useCases.insertLog.execute(call) }

    getInclusive("$apiBase/logs/{id}") { useCases.getById.execute(call) }

    deleteInclusive("$apiBase/logs/{id}") { useCases.deleteById.execute(call) }
}
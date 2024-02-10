package io.github.elkhoudiry.klogger.server.route.events

import io.github.elkhoudiry.klogger.server.data.events.repositories.DefaultEventsRepository
import io.github.elkhoudiry.klogger.server.route.common.deleteInclusive
import io.github.elkhoudiry.klogger.server.route.common.getInclusive
import io.github.elkhoudiry.klogger.server.route.common.postInclusive
import io.github.elkhoudiry.klogger.server.route.events.usecases.EventsRouteUseCases
import io.ktor.server.application.call
import io.ktor.server.routing.Routing

fun Routing.events(
    repository: DefaultEventsRepository
) {
    val useCases = EventsRouteUseCases(repository = repository)

    getInclusive("/api/v1/events") { useCases.getAll.execute(call) }

    postInclusive("/api/v1/events") { useCases.insertLog.execute(call) }

    getInclusive("/api/v1/events/{id}") { useCases.getById.execute(call) }

    deleteInclusive("/api/v1/events/{id}") { useCases.deleteById.execute(call) }
}
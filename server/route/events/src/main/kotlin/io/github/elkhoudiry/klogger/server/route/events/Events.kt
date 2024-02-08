package io.github.elkhoudiry.klogger.server.route.events

import io.github.elkhoudiry.klogger.server.data.events.repositories.DefaultEventsRepository
import io.github.elkhoudiry.klogger.server.data.events.repositories.EventsRepository
import io.github.elkhoudiry.klogger.server.route.common.deleteInclusive
import io.github.elkhoudiry.klogger.server.route.common.getInclusive
import io.github.elkhoudiry.klogger.server.route.common.postInclusive
import io.github.elkhoudiry.klogger.server.route.events.usecases.EventsRouteUseCases
import io.ktor.server.application.call
import io.ktor.server.routing.Routing

private val events: EventsRepository = DefaultEventsRepository()
private val useCases = EventsRouteUseCases(events = events)

fun Routing.events() {
    getInclusive("/api/v1/events") { useCases.getAll.execute(call) }

    postInclusive("/api/v1/events") { useCases.insertLog.execute(call) }

    getInclusive("/api/v1/events/{id}") { useCases.getById.execute(call) }

    deleteInclusive("/api/v1/events/{id}") { useCases.deleteById.execute(call) }
}
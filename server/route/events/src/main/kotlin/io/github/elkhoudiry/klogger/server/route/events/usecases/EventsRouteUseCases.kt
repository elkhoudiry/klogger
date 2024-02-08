package io.github.elkhoudiry.klogger.server.route.events.usecases

import io.github.elkhoudiry.klogger.server.data.events.repositories.EventsRepository

internal class EventsRouteUseCases(
    events: EventsRepository
) {
    val insertLog = InsertEventUseCase(
        events = events
    )

    val getById = GetByIdUseCase(
        events = events
    )

    val getAll = GetAllUseCase(
        events = events
    )

    val deleteById = DeleteByIdUseCase(
        events = events
    )
}
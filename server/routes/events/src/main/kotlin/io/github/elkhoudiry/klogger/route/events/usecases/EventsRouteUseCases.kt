package io.github.elkhoudiry.klogger.route.events.usecases

import io.github.elkhoudiry.server.data.events.repositories.EventsRepository

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
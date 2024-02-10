package io.github.elkhoudiry.klogger.server.route.events.usecases

import io.github.elkhoudiry.klogger.server.data.events.repositories.EventsRepository

internal class EventsRouteUseCases(
    repository: EventsRepository
) {
    val insertLog = InsertEventUseCase(
        events = repository
    )

    val getById = GetByIdUseCase(
        events = repository
    )

    val getAll = GetAllUseCase(
        events = repository
    )

    val deleteById = DeleteByIdUseCase(
        events = repository
    )
}
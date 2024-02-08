package io.github.elkhoudiry.klogger.route.events.usecases

import io.github.elkhoudiry.klogger.core.shared.serialization.json
import io.github.elkhoudiry.klogger.server.data.common.models.notFound
import io.github.elkhoudiry.server.data.events.models.Event
import io.github.elkhoudiry.server.data.events.repositories.EventsRepository
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

internal class GetByIdUseCase(
    private val events: EventsRepository
) {

    suspend fun execute(call: ApplicationCall) {
        val id = call.parameters["id"] ?: notFound()
        call.respond(json.encodeToJsonElement(Event.serializer(), events.getById(id) ?: notFound()))
    }
}
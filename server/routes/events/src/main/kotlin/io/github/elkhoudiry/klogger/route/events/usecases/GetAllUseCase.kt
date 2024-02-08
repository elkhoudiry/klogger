package io.github.elkhoudiry.klogger.route.events.usecases

import io.github.elkhoudiry.klogger.core.shared.serialization.json
import io.github.elkhoudiry.server.data.events.models.Event
import io.github.elkhoudiry.server.data.events.repositories.EventsRepository
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import kotlinx.serialization.builtins.ListSerializer

internal class GetAllUseCase(
    private val events: EventsRepository
) {

    suspend fun execute(call: ApplicationCall) {
        call.respond(json.encodeToJsonElement(ListSerializer(Event.serializer()), events.getAll()))
    }
}
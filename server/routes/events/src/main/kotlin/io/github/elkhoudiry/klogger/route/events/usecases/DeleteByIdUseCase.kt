package io.github.elkhoudiry.klogger.route.events.usecases

import io.github.elkhoudiry.klogger.server.data.common.models.notFound
import io.github.elkhoudiry.server.data.events.repositories.EventsRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

internal class DeleteByIdUseCase(
    private val events: EventsRepository
) {

    suspend fun execute(call: ApplicationCall) {
        val id = call.parameters["id"] ?: notFound()

        call.respond(if (events.deleteById(id)) HttpStatusCode.Accepted else notFound())
    }
}
package io.github.elkhoudiry.klogger.server.route.logs.usecases

import io.github.elkhoudiry.klogger.server.data.common.models.notFound
import io.github.elkhoudiry.klogger.server.data.logs.repositories.LoggerRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

internal class DeleteByIdUseCase(
    private val logging: LoggerRepository
) {

    suspend fun execute(call: ApplicationCall) {
        val id = call.parameters["id"] ?: notFound()

        call.respond(if (logging.deleteById(id)) HttpStatusCode.Accepted else notFound())
    }
}
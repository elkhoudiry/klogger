package io.github.elkhoudiry.klogger.route.logs.usecases

import io.github.elkhoudiry.klogger.core.shared.serialization.json
import io.github.elkhoudiry.klogger.server.data.common.models.notFound
import io.github.elkhoudiry.server.data.logger.models.Log
import io.github.elkhoudiry.server.data.logger.repositories.LoggerRepository
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

internal class GetByIdUseCase(
    private val logging: LoggerRepository
) {

    suspend fun execute(call: ApplicationCall) {
        val id = call.parameters["id"] ?: notFound()
        call.respond(json.encodeToJsonElement(Log.serializer(), logging.getById(id) ?: notFound()))
    }
}
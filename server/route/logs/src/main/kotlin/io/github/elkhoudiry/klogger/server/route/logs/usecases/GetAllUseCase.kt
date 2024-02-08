package io.github.elkhoudiry.klogger.server.route.logs.usecases

import io.github.elkhoudiry.klogger.core.shared.serialization.json
import io.github.elkhoudiry.klogger.server.data.logs.models.Log
import io.github.elkhoudiry.klogger.server.data.logs.repositories.LoggerRepository
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import kotlinx.serialization.builtins.ListSerializer

internal class GetAllUseCase(
    private val logging: LoggerRepository
) {

    suspend fun execute(call: ApplicationCall) {
        call.respond(json.encodeToJsonElement(ListSerializer(Log.serializer()), logging.getAll()))
    }
}
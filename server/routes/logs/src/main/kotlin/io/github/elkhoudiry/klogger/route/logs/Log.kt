package io.github.elkhoudiry.klogger.route.logs

import io.github.elkhoudiry.klogger.core.shared.serialization.json
import io.github.elkhoudiry.klogger.route.logs.usecases.LogRouteUseCases
import io.github.elkhoudiry.server.data.logger.models.Log
import io.github.elkhoudiry.server.data.logger.repositories.DefaultLoggerRepository
import io.github.elkhoudiry.server.data.logger.repositories.LoggerRepository
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.serialization.builtins.ListSerializer

private val logger: LoggerRepository = DefaultLoggerRepository()
private val useCases = LogRouteUseCases(logging = logger)

fun Routing.log(apiBase: String) {
    get("$apiBase/logs") {
        call.respond(json.encodeToJsonElement(ListSerializer(Log.serializer()), logger.getAll()))
    }

    post("$apiBase/logs") {
        useCases.insertLog.execute(call)
    }
}
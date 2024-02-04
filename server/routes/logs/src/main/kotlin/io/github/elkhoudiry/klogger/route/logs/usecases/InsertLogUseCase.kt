package io.github.elkhoudiry.klogger.route.logs.usecases

import io.github.elkhoudiry.klogger.core.shared.serialization.json
import io.github.elkhoudiry.klogger.core.shared.serialization.parseObject
import io.github.elkhoudiry.klogger.server.data.common.models.badRequest
import io.github.elkhoudiry.klogger.server.data.common.serialization.arrayOrNull
import io.github.elkhoudiry.klogger.server.data.common.serialization.objectOrNull
import io.github.elkhoudiry.klogger.server.data.common.serialization.stringOrReject
import io.github.elkhoudiry.server.data.logger.models.Log
import io.github.elkhoudiry.server.data.logger.models.LogLevel
import io.github.elkhoudiry.server.data.logger.models.LogProperty
import io.github.elkhoudiry.server.data.logger.models.LogType
import io.github.elkhoudiry.server.data.logger.repositories.LoggerRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

internal class InsertLogUseCase(
    private val logging: LoggerRepository
) {

    suspend fun execute(call: ApplicationCall) {
        val log = getLog(call)

        logging.insert(log)

        call.respond(HttpStatusCode.Created, buildJsonObject { put("id", log.id) })
    }

    private suspend fun getLog(call: ApplicationCall): Log {
        val json = json.parseObject(call.receiveText()) ?: badRequest()

        return Log(
            log = json.stringOrReject("log"),
            level = LogLevel.valueOf(json.stringOrReject("level")),
            type = LogType.valueOf(json.stringOrReject("type")),
            clientDateTime = json.stringOrReject("datetime"),
            properties = getProperties(json["properties"])
        )
    }

    private fun getProperties(element: JsonElement?): List<LogProperty> {
        val json = element ?: return emptyList()
        val properties = json.arrayOrNull ?: badRequest()

        return properties.map { getProperty(it) }
    }

    private fun getProperty(element: JsonElement): LogProperty {
        val property = element.objectOrNull ?: badRequest()

        return LogProperty(
            name = property.stringOrReject("name"),
            value = property.stringOrReject("value")
        )
    }
}
package io.github.elkhoudiry.klogger.server.route.logs.usecases

import io.github.elkhoudiry.klogger.core.shared.platform.uuid
import io.github.elkhoudiry.klogger.core.shared.serialization.json
import io.github.elkhoudiry.klogger.core.shared.serialization.parseObject
import io.github.elkhoudiry.klogger.server.data.common.models.badRequest
import io.github.elkhoudiry.klogger.server.data.common.serialization.arrayOrNull
import io.github.elkhoudiry.klogger.server.data.common.serialization.objectOrNull
import io.github.elkhoudiry.klogger.server.data.common.serialization.stringOrReject
import io.github.elkhoudiry.klogger.server.data.logs.models.Log
import io.github.elkhoudiry.klogger.server.data.logs.models.LogLevel
import io.github.elkhoudiry.klogger.server.data.logs.models.LogProperty
import io.github.elkhoudiry.klogger.server.data.logs.models.LogType
import io.github.elkhoudiry.klogger.server.data.logs.repositories.LoggerRepository
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
        val id = uuid()

        return Log(
            id = id,
            log = json.stringOrReject("log"),
            level = json.stringOrReject("level", LogLevel::class),
            type = json.stringOrReject("type", LogType::class),
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
        val id = uuid()
        return LogProperty(
            id = id,
            name = property.stringOrReject("name"),
            value = property.stringOrReject("value")
        )
    }
}
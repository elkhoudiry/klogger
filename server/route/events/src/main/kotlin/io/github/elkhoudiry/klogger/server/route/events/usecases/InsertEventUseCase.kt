package io.github.elkhoudiry.klogger.server.route.events.usecases

import io.github.elkhoudiry.klogger.core.shared.platform.uuid
import io.github.elkhoudiry.klogger.core.shared.serialization.json
import io.github.elkhoudiry.klogger.core.shared.serialization.parseObject
import io.github.elkhoudiry.klogger.server.data.common.models.badRequest
import io.github.elkhoudiry.klogger.server.data.common.serialization.arrayOrNull
import io.github.elkhoudiry.klogger.server.data.common.serialization.objectOrNull
import io.github.elkhoudiry.klogger.server.data.common.serialization.stringOrReject
import io.github.elkhoudiry.klogger.server.data.events.models.Event
import io.github.elkhoudiry.klogger.server.data.events.models.EventProperty
import io.github.elkhoudiry.klogger.server.data.events.repositories.EventsRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

internal class InsertEventUseCase(
    private val events: EventsRepository
) {

    suspend fun execute(call: ApplicationCall) {
        val event = getEvent(call)

        events.insert(event)

        call.respond(HttpStatusCode.Created, buildJsonObject { put("id", event.id) })
    }

    private suspend fun getEvent(call: ApplicationCall): Event {
        val json = json.parseObject(call.receiveText()) ?: badRequest()
        val id = uuid()
        return Event(
            id = id,
            clientDateTime = json.stringOrReject("datetime"),
            properties = getProperties(json["properties"]),
            description = json.stringOrReject("description"),
            type = json.stringOrReject("type", Event.Type::class)
        )
    }

    private fun getProperties(element: JsonElement?): List<EventProperty> {
        val json = element ?: return emptyList()
        val properties = json.arrayOrNull ?: badRequest()

        return properties.map { getProperty(it) }
    }

    private fun getProperty(element: JsonElement): EventProperty {
        val property = element.objectOrNull ?: badRequest()
        val id = uuid()

        return EventProperty(
            id = id,
            name = property.stringOrReject("name"),
            value = property.stringOrReject("value")
        )
    }
}
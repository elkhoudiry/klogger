package io.github.elkhoudiry.klogger.server.data.events.models

import io.github.elkhoudiry.klogger.server.data.database.common.events.models.EventEntity
import io.github.elkhoudiry.klogger.server.data.database.common.events.models.EventPropertyEntity

internal fun EventEntity.toModel(): Event {
    return Event(
        id = id,
        type = Event.Type.valueOf(type),
        description = description,
        properties = properties.map { it.toModel() },
        serverDateTime = serverDateTime,
        clientDateTime = clientDateTime
    )
}

internal fun EventPropertyEntity.toModel(): EventProperty {
    return EventProperty(
        id = id,
        name = name,
        value = value
    )
}

internal fun Event.toEntity(): EventEntity {
    return EventEntity(
        id = id,
        type = type.name,
        description = description,
        properties = properties.map { it.toEntity() },
        serverDateTime = serverDateTime,
        clientDateTime = clientDateTime
    )
}

internal fun EventProperty.toEntity(): EventPropertyEntity {
    return EventPropertyEntity(
        id = id,
        name = name,
        value = value
    )
}
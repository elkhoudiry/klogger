package io.github.elkhoudiry.klogger.server.data.logs.models

import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogEntity
import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogPropertyEntity

internal fun LogEntity.toLog(): Log {
    return Log(
        id = id,
        log = log,
        type = LogType.valueOf(type),
        level = LogLevel.valueOf(level),
        properties = properties.map { it.toLogProperty() },
        serverDateTime = serverDateTime,
        clientDateTime = clientDateTime
    )
}

internal fun Log.toLogEntity(): LogEntity {
    return LogEntity(
        id = id,
        log = log,
        type = type.name,
        level = level.name,
        properties = properties.map { it.toLogPropertyEntity() },
        serverDateTime = serverDateTime,
        clientDateTime = clientDateTime
    )
}

internal fun LogPropertyEntity.toLogProperty(): LogProperty {
    return LogProperty(
        id = id,
        name = name,
        value = value
    )
}

internal fun LogProperty.toLogPropertyEntity(): LogPropertyEntity {
    return LogPropertyEntity(
        id = id,
        name = name,
        value = value
    )
}
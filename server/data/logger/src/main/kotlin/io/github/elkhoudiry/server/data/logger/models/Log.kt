package io.github.elkhoudiry.server.data.logger.models

import com.benasher44.uuid.uuid4
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

enum class LogLevel {
    DEBUG, INFO, WARN, ERROR
}

enum class LogType {
    START, END, INFO
}

@Serializable
data class Log(
    val log: String,
    val type: LogType,
    val level: LogLevel,
    val properties: List<LogProperty>,
    val clientDateTime: String
) {
    val serverDateTime: String = Clock.System
        .now()
        .toString()

    val id: String = uuid4().toString()
}

@Serializable
data class LogProperty(
    val id: String = uuid4().toString(),
    val name: String,
    val value: String
)
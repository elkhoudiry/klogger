package io.github.elkhoudiry.server.data.logger.models

import kotlinx.datetime.Clock
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

enum class LogLevel {
    DEBUG, INFO, WARN, ERROR
}

enum class LogType {
    START, END, INFO
}

@Serializable
data class Log(
    val id: String,
    val log: String,
    val type: LogType,
    val level: LogLevel,
    val properties: List<LogProperty>,
    @SerialName("datetime")
    val serverDateTime: String = Clock.System
        .now()
        .toString(),

    @Transient
    val clientDateTime: String = serverDateTime
)

@Serializable
data class LogProperty(
    val id: String,
    val name: String,
    val value: String
)
package io.github.elkhoudiry.klogger.client.data.logger.models

import kotlinx.datetime.Clock

enum class LogLevel {
    DEBUG, INFO, WARN, ERROR
}

sealed interface Log {
    val localDatetime: String
    val level: LogLevel
}

data class LogProperty(val property: String, val value: Any)

data class EventLog(
    val type: Type,
    val description: String,
    val context: List<LogProperty>,
    override val level: LogLevel
) : Log {
    override val localDatetime: String = Clock.System.now().toString()

    enum class Type {
        User { override fun toString(): String = "user" },
        Screen { override fun toString(): String = "screen" },
        Foreground { override fun toString(): String = "foreground" },
        Background { override fun toString(): String = "background" },
        HttpRequest { override fun toString(): String = "http_request" },
        HttpResponse { override fun toString(): String = "http_response" },
        Click { override fun toString(): String = "click" },
        Swipe { override fun toString(): String = "swipe" },
        Scroll { override fun toString(): String = "scroll" },
        Back { override fun toString(): String = "back" }
    }
}

sealed interface ExecutionLog : Log {
    val log: String
    val context: List<LogProperty>
    data class Start(
        override val log: String,
        override val context: List<LogProperty>,
        override val localDatetime: String = Clock.System.now().toString(),
        override val level: LogLevel = LogLevel.DEBUG
    ) : ExecutionLog

    data class End(
        override val log: String,
        override val context: List<LogProperty>,
        override val localDatetime: String = Clock.System.now().toString(),
        override val level: LogLevel = LogLevel.DEBUG
    ) : ExecutionLog

    data class Info(
        override val log: String,
        override val context: List<LogProperty>,
        override val localDatetime: String = Clock.System.now().toString(),
        override val level: LogLevel = LogLevel.INFO
    ) : ExecutionLog
}
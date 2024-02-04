package io.github.elkhoudiry.server.data.logger.models

import kotlinx.datetime.Clock

class EventProperty(val id: String, val eventId: String, val name: String, val value: String)

data class Event(
    val type: Type,
    val description: String,
    val context: List<EventProperty>,
    val id: String,
    val level: LogLevel
) {
    val localDatetime: String = Clock.System
        .now()
        .toString()

    enum class Type {
        User {
            override fun toString(): String = "user"
        },
        Screen {
            override fun toString(): String = "screen"
        },
        Foreground {
            override fun toString(): String = "foreground"
        },
        Background {
            override fun toString(): String = "background"
        },
        HttpRequest {
            override fun toString(): String = "http_request"
        },
        HttpResponse {
            override fun toString(): String = "http_response"
        },
        Click {
            override fun toString(): String = "click"
        },
        Input {
            override fun toString(): String = "input"
        },
        Swipe {
            override fun toString(): String = "swipe"
        },
        Scroll {
            override fun toString(): String = "scroll"
        },
        Back {
            override fun toString(): String = "back"
        }
    }
}
package io.github.elkhoudiry.klogger.server.data.events.models

import kotlinx.datetime.Clock
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Event(
    val type: Type,
    val description: String,
    val properties: List<EventProperty>,
    val id: String,
    @SerialName("datetime")
    val serverDateTime: String = Clock.System
        .now()
        .toString(),

    @Transient
    val clientDateTime: String = serverDateTime
) {
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

@Serializable
class EventProperty(val id: String, val name: String, val value: String)
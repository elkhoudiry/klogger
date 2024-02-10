package io.github.elkhoudiry.klogger.server.data.database.common.events.models

data class EventEntity(
    val id: String,
    val type: String,
    val description: String,
    val properties: List<EventPropertyEntity>,
    val serverDateTime: String,
    val clientDateTime: String
)

class EventPropertyEntity(val id: String, val name: String, val value: String)
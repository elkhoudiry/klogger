package io.github.elkhoudiry.klogger.server.data.database.common.logs.models

data class LogEntity(
    val id: String,
    val log: String,
    val type: String,
    val level: String,
    val properties: List<LogPropertyEntity>,
    val serverDateTime: String,
    val clientDateTime: String
)

data class LogPropertyEntity(
    val id: String,
    val name: String,
    val value: String
)
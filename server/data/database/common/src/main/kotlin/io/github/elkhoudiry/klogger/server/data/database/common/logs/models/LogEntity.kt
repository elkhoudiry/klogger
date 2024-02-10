package io.github.elkhoudiry.klogger.server.data.database.common.logs.models

data class LogEntity(
    val id: String,
    val log: String,
    val type: String,
    val level: String,
    val properties: List<LogProperty>,
    val serverDateTime: String,
    val clientDateTime: String
)

data class LogProperty(
    val id: String,
    val logId: String,
    val name: String,
    val value: String
)
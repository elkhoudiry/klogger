package io.github.elkhoudiry.klogger.client.data.logger

import io.github.elkhoudiry.klogger.client.data.logger.models.EventLog
import io.github.elkhoudiry.klogger.client.data.logger.models.ExecutionLog
import io.github.elkhoudiry.klogger.client.data.logger.models.LogLevel
import io.github.elkhoudiry.klogger.client.data.logger.models.LogProperty

actual class Logger constructor() {
    fun initialize() {
    }

    internal actual fun startWork(message: String) {
        ExecutionLog.Start(log = message, context = listOf())
    }

    internal actual fun finishWork(message: String) {
        ExecutionLog.End(log = message, context = listOf())
    }

    actual fun debug(
        message: String,
        context: List<LogProperty>
    ) {
        ExecutionLog.Info(message, context, level = LogLevel.DEBUG)
    }

    actual fun info(
        message: String,
        context: List<LogProperty>
    ) {
    }

    actual fun warn(
        message: String,
        context: List<LogProperty>
    ) {
    }

    actual fun error(
        message: String,
        context: List<LogProperty>
    ) {
    }

    actual fun event(
        type: EventLog.Type,
        description: String,
        context: List<LogProperty>
    ) {
    }
}
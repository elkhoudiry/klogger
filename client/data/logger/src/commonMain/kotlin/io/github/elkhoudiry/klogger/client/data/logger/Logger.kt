package io.github.elkhoudiry.klogger.client.data.logger

import io.github.elkhoudiry.klogger.client.data.logger.models.ExecutionScope
import io.github.elkhoudiry.klogger.client.data.logger.models.ExecutionLog
import io.github.elkhoudiry.klogger.client.data.logger.models.LogProperty
import io.github.elkhoudiry.klogger.core.shared.errors.CriticalThrow
import io.github.elkhoudiry.klogger.core.shared.errors.Throw
import io.github.elkhoudiry.klogger.core.shared.errors.toError
import io.github.elkhoudiry.klogger.core.shared.platform.Platform
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

expect class Logger() {

    fun initialize()

    fun debug(message: String, context: List<LogProperty>)

    fun info(message: String, context: List<LogProperty>)

    fun warn(message: String, context: List<LogProperty>)

    fun error(message: String, context: List<LogProperty>)

}

inline fun <T> Logger.log(
    crossinline block: ExecutionScope<T>.() -> T
): T {
    debug("${Platform.executeLocation()}: started", listOf())
    val result = ExecutionScope<T>(Platform.executeLocation(), EmptyCoroutineContext).execute {
        try {
            block()
        } catch (ex: Exception) {
            if (ex is CriticalThrow) {
                throw ex.regression(location = Platform.executeLocation(),  cause = ex, details = toDetails())
            } else {
                throw ex.toError(details = toDetails())
            }
        }
    }

    debug("${Platform.executeLocation()}: finished", listOf())
    return result
}

suspend inline fun <T> Logger.async(
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend ExecutionScope<T>.() -> T
): T = withContext(currentCoroutineContext() + context) {
    debug("${Platform.executeLocation()}: started", listOf())

    val result = ExecutionScope<T>(Platform.executeLocation(), currentCoroutineContext()).executeSuspend {
        try {
            block()
        } catch (ex: Exception) {
            if (ex is CriticalThrow) {
                throw ex.regression(location = Platform.executeLocation(), cause = ex, details = toDetails())
            } else {
                throw ex.toError(details = toDetails())
            }
        }
    }

    debug("${Platform.executeLocation()}: finished", listOf())
    return@withContext result
}
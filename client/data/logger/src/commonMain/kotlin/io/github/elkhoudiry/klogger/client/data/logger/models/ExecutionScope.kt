package io.github.elkhoudiry.klogger.client.data.logger.models

import io.github.elkhoudiry.klogger.client.data.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class ExecutionScope<T>(
    private val executeLocation: String,
    private val context: CoroutineContext
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = context

    private val details = mutableMapOf<String, Any>()

    fun Logger.context(block: () -> Pair<String, Any>) {
        val (key, value) = block()
        details["$executeLocation>$key"] = value
        debug("$executeLocation>$key: $value", details.map { LogProperty(it.key, it.value) })
    }

    fun execute(block: ExecutionScope<T>.() -> T): T {
        return block(this)
    }

    suspend fun executeSuspend(block: suspend ExecutionScope<T>.() -> T): T {
        return block(this)
    }

    fun toDetails():Map<String, Any> {
        return details
    }
}
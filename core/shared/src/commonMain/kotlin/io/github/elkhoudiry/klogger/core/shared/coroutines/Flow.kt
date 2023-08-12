package io.github.elkhoudiry.klogger.core.shared.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.onError(action: suspend (Throwable) -> Unit): Flow<T> = flow {
    try {
        collect { emit(it) }
    } catch (e: Throwable) {
        action(e)
    }
}

fun <T> streamFlow(ignoreCancellation: Boolean = true, block: suspend FlowCollector<T>.() -> Unit): Flow<T> {
    return flow {
        try {
            block()
        } catch (ex: Exception) {
            if (ex is CancellationException && ignoreCancellation) {
                return@flow
            } else {
                throw ex
            }
        }
    }
}
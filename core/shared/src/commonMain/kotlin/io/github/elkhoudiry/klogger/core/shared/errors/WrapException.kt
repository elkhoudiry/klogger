package io.github.elkhoudiry.klogger.core.shared.errors

import io.github.elkhoudiry.klogger.core.shared.message.Message
import io.github.elkhoudiry.klogger.core.shared.message.message
import io.github.elkhoudiry.klogger.core.shared.platform.Platform
import io.github.elkhoudiry.klogger.core.shared.platform.Platform.exceptionLocation

inline fun Exception.toError(details: Map<String, Any>, message: Message? = null): Error {
    return when (this) {
        is Info -> this

        is Report -> this.regression(
            location = Platform.executeLocation(),
            details = details,
            cause = this
        )

        else -> Unexpected(
            location = Platform.executeLocation(),
            details = details.toMap(),
            display = message ?: message(this.message ?: "Unexpected error happened"),
            cause = this
        )
    }
}

fun Throwable.debugString(): String {
    return "class: ${this::class.simpleName}, message: ${this.message} at ${exceptionLocation()}"
}

fun Exception.debugString(): String {
    return "class: ${this::class.simpleName}, message: ${this.message} at ${exceptionLocation()}"
}
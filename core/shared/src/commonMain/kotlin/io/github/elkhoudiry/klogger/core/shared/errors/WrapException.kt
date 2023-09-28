package io.github.elkhoudiry.klogger.core.shared.errors

import io.github.elkhoudiry.klogger.core.shared.message.Message
import io.github.elkhoudiry.klogger.core.shared.message.message
import io.github.elkhoudiry.klogger.core.shared.platform.Platform
import io.github.elkhoudiry.klogger.core.shared.platform.Platform.exceptionLocation

inline fun Exception.toError( details: Map<String, Any>, message: Message? = null): Throw {
    return when (this) {
        is InfoThrow -> this
        is CriticalThrow -> this.regression(
            location = Platform.executeLocation(),
            details = details,
            message = message ?: message(this.message),
            cause = this
        )
        else -> CriticalThrow(
            callLocation = Platform.executeLocation(),
            details = details.toMap(),
            message = message ?: message(this.message),
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
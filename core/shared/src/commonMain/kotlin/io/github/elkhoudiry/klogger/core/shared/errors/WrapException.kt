package io.github.elkhoudiry.klogger.core.shared.errors

import io.github.elkhoudiry.klogger.core.shared.platform.Platform
import io.github.elkhoudiry.klogger.core.shared.platform.Platform.exceptionLocation
import io.github.elkhoudiry.klogger.core.shared.status.models.Message

inline fun Exception.toError(details: Array<String>): Throw {
    return when (this) {
        is Throw.Info -> this
        is Throw.Critical -> this.regression(
            location = Platform.executeLocation(),
            details = details
        )
        else -> Throw.Critical.UnExpected(
            callSite = Platform.executeLocation(),
            details = details,
            message = Message.fromString(
                this.message ?: ("Error message not found")
            )
        )
    }
}

fun Throwable.debugString(): String {
    return "class: ${this::class.simpleName}, message: ${this.message} at ${exceptionLocation()}"
}

fun Exception.debugString(): String {
    return "class: ${this::class.simpleName}, message: ${this.message} at ${exceptionLocation()}"
}
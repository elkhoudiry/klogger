package io.github.elkhoudiry.klogger.core.shared.errors

import com.benasher44.uuid.uuid4
import io.github.elkhoudiry.klogger.core.shared.message.Message
import io.github.elkhoudiry.klogger.core.shared.message.message
import io.github.elkhoudiry.klogger.core.shared.platform.Platform

sealed class Error(val display: Message, cause: Throwable) : Exception(cause)

class Info(display: Message) : Error(display, Exception(display.get()))

abstract class Report(
    display: Message,
    cause: Throwable,
    location: String,
    details: Map<String, Any?>,
    val uid: String = uuid4().toString()
) : Error(display, cause) {

    private var isReported = false
    private val _locations = mutableListOf(location)
    private val _details = details.toMutableMap()
    val locations: List<String> = _locations
    val details: Map<String, Any?> = _details

    fun reported() {
        isReported = true
    }

    fun isReported(): Boolean {
        return isReported
    }

    fun regression(
        cause: Throwable,
        location: String,
        details: Map<String, Any?>
    ): Report {
        this._locations.add(location)
        this._details.putAll(details)

        cause.addSuppressed(this)

        return this
    }

    inline fun mask(message: Message): Report {
        return Unexpected(
            display = message,
            cause = cause!!,
            location = Platform.executeLocation(),
            details = details
        )
    }

    constructor(cause: Exception, callLocation: String, details: Map<String, Any?> = mapOf()) : this(
        display = message(cause.message ?: "Unexpected error happened"),
        cause = cause,
        location = callLocation,
        details = details
    )

    constructor(message: Message, callLocation: String, details: Map<String, Any?> = mapOf()) : this(
        display = message,
        cause = Exception(message.get()),
        location = callLocation,
        details = details
    )

    constructor(message: String, callLocation: String, details: Map<String, Any?> = mapOf()) : this(
        display = message(message),
        cause = Exception(message),
        location = callLocation,
        details = details
    )
}

class Unexpected(
    cause: Throwable,
    display: Message = message(
        cause.message?.ifBlank { "Unexpected error happened" } ?: "Unexpected error happened"
    ),
    location: String,
    details: Map<String, Any?>
) : Report(
    display = display,
    cause = cause,
    location = location,
    details = details
)
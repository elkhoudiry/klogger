package io.github.elkhoudiry.klogger.core.server.models

import io.github.elkhoudiry.klogger.core.shared.errors.CriticalThrow
import io.github.elkhoudiry.klogger.core.shared.message.Message
import io.github.elkhoudiry.klogger.core.shared.message.message
import io.github.elkhoudiry.klogger.core.shared.platform.Platform

abstract class ResponseException(
    val technicalMessage: String,
    val friendlyMessage: Message,
    location: String,
    cause: Exception
) : CriticalThrow(
    message = friendlyMessage,
    cause = cause,
    callLocation = location,
    details = mapOf("technical_message" to technicalMessage)
)

class InvalidBodyException(
    technicalMessage: String,
    friendlyMessage: Message,
    location: String,
    cause: Exception
) : ResponseException(
    friendlyMessage = friendlyMessage, technicalMessage = technicalMessage, location = location, cause = cause
)

class MissingPropertyException(
    property: String,
    location: String,
    friendlyMessage: Message? = null
) : ResponseException(
    technicalMessage = "Property $property is missing.",
    friendlyMessage = friendlyMessage ?: message("Property $property is missing."),
    location = location,
    cause = Exception("Property $property is missing.")
)

@Suppress("NOTHING_TO_INLINE")
inline fun missingProperty(
    property: String,
    friendlyMessage: Message? = null,
    location: String = Platform.executeLocation(),
): Nothing = throw MissingPropertyException(property, location, friendlyMessage)
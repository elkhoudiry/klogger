package io.github.elkhoudiry.klogger.server.core

open class ResponseException(
    val technicalMessage: String,
    val friendlyMessage: String,
    cause: Exception
) : Exception(cause)

class InvalidBodyException(
    technicalMessage: String,
    friendlyMessage: String,
    cause: Exception
) : ResponseException(friendlyMessage = friendlyMessage, technicalMessage = technicalMessage, cause = cause)

class MissingPropertyException(
    property: String,
    friendlyMessage: String? = null
) : ResponseException(
    technicalMessage = "Property $property is missing.",
    friendlyMessage = friendlyMessage ?: "Property $property is missing.",
    cause = Exception("Property $property is missing.")
)

fun missingProperty(property: String): Nothing = throw MissingPropertyException(property)
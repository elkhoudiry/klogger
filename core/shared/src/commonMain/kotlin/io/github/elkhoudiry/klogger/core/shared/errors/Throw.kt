package io.github.elkhoudiry.klogger.core.shared.errors

import com.benasher44.uuid.uuid4
import io.github.elkhoudiry.klogger.core.shared.message.Message

sealed class Throw(val errorMessage: Message, cause: Throwable) : Exception(cause)

class InfoThrow(message: Message) : Throw(message, Exception(message.get()))

open class CriticalThrow(
    message: Message,
    cause: Throwable,
    callLocation: String,
    details: Map<String, Any>,
    val uid: String = uuid4().toString()
) : Throw(message, cause) {

    private var isReported = false
    val callLocation = arrayListOf(callLocation)
    val details = details.toMutableMap()

    fun reported() {
        isReported = true
    }

    fun isReported(): Boolean {
        return isReported
    }

    inline fun regression(
        message: Message? = null,
        cause: Throwable,
        location: String,
        details: Map<String, Any>
    ): CriticalThrow {
        this.callLocation.add(0, location)
        this.details.putAll(details.toMap())

        cause.addSuppressed(this)

        return CriticalThrow(
            message = message ?: this.errorMessage,
            cause = cause,
            callLocation = location,
            details = details.toMap()
        )
    }
}
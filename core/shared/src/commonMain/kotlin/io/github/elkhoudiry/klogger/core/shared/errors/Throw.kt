package io.github.elkhoudiry.klogger.core.shared.errors

import com.benasher44.uuid.uuid4
import io.github.elkhoudiry.klogger.core.shared.status.models.Message

sealed class Throw(
    val errorMessage: Message,
    cause: Throwable
) : Exception(errorMessage.en, cause) {

    class Info(message: Message) : Throw(message, Throwable())

    abstract class Critical(
        message: Message,
        cause: Throwable,
        callLocation: String,
        val uid: String = uuid4().toString(),
        details: Array<String>
    ) : Throw(message, cause) {

        private var isReported = false
        private var callLocation = callLocation
        private val details = arrayListOf(*details)

        fun reported() {
            isReported = true
        }

        fun isReported(): Boolean {
            return isReported
        }

        fun regression(
            location: String,
            vararg details: String
        ): Critical {
            this.callLocation = "$location\n$callLocation"
            this.details.addAll(0, details.toList())

            return this
        }

        fun getCallLocation(): String {
            return callLocation
        }

        fun getThrowDetails(): List<String> {
            return details
        }

        class UnExpected(
            cause: Throwable? = null,
            message: Message = if (cause?.message != null) {
                Message.fromString(cause.message)
            } else {
                Message.fromString("Unexpected error happened")
            },
            callSite: String,
            details: Array<String> = arrayOf()
        ) : Critical(
            message = message,
            cause = cause ?: Exception(message.en),
            callLocation = callSite,
            details = details
        )
    }

    fun message(): String {
        return message ?: "message error"
    }
}
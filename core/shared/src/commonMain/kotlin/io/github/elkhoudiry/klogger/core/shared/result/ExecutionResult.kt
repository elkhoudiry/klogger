package io.github.elkhoudiry.klogger.core.shared.result

import io.github.elkhoudiry.klogger.core.shared.errors.Error
import io.github.elkhoudiry.klogger.core.shared.errors.Report
import io.github.elkhoudiry.klogger.core.shared.message.Message
import io.github.elkhoudiry.klogger.core.shared.message.message

sealed interface ExecutionResult<T> {

    class Success<T>(override val data: T) : ExecutionResult<T>

    class Fail<T>(val error: Error, val placeholder: T? = null) : ExecutionResult<T>

    val data: T
        get() {
            return when (this) {
                is Fail -> {
                    throw error
                }

                is Success -> (this as Success).data
            }
        }

    val resultOrNull: T?
        get() {
            return when (this) {
                is Fail -> null
                is Success -> (this as Success).data
            }
        }

    fun resultOrMask(mask: String): T {
        return when (this) {
            is Fail -> {
                throw error.mask(message(mask))
            }

            is Success -> (this as Success).data
        }
    }

    fun resultOrMask(mask: Message): T {
        return when (this) {
            is Fail -> {
                throw error.mask(mask)
            }

            is Success -> (this as Success).data
        }
    }

    private fun Error.mask(message: Message): Error {
        return when (this) {
            is Report -> mask(message)
            else -> this
        }
    }
}
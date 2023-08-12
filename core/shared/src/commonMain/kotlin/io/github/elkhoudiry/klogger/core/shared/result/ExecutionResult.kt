package io.github.elkhoudiry.klogger.core.shared.result

import io.github.elkhoudiry.klogger.core.shared.errors.Throw

sealed interface ExecutionResult<T> {

    class Success<T>(override val data: T) : ExecutionResult<T>

    class Fail<T>(val error: Throw, val placeholder: T? = null) : ExecutionResult<T>

    val data: T
        get() {
            return when (this) {
                is Fail -> { throw error }
                is Success -> (this).data
            }
        }
}
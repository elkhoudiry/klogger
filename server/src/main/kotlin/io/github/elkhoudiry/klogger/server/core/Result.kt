package io.github.elkhoudiry.klogger.server.core

sealed interface Result<T> {

    data class Success<T>(val data: T) : Result<T>

    data class Failure<T>(val error: Exception) : Result<T>

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> failure(error: Exception) = Failure<T>(error)
    }
}
package io.github.elkhoudiry.klogger.core.shared.result

import io.github.elkhoudiry.klogger.core.shared.errors.Throw
import io.github.elkhoudiry.klogger.core.shared.platform.Platform
import io.github.elkhoudiry.klogger.core.shared.status.models.Message

sealed interface AsyncResult<T> {

    class Success<T>(override val result: T) : AsyncResult<T>

    class Fail<T>(val error: Throw, val data: T? = null) : AsyncResult<T>

    val result: T
        get() {
            return when (this) {
                is Fail -> {
                    throw Throw.Critical.UnExpected(
                        message = Message.fromString("Execution failed"),
                        callSite = Platform.executeLocation(),
                        details = arrayOf("Async Result")
                    )
                }
                is Success -> (this as Success).result
            }
        }
}
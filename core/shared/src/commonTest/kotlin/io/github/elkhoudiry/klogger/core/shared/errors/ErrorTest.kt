package io.github.elkhoudiry.klogger.core.shared.errors

import io.github.elkhoudiry.klogger.core.shared.message.message
import io.github.elkhoudiry.klogger.core.shared.platform.Platform.exceptionLocation
import kotlin.test.Test
import kotlin.test.assertIs

class ErrorTest {

    @Test
    fun testClass() {
        val infoError = Info(display = message("test message."))
        val error = Unexpected(
            display = message("test message."),
            cause = NullPointerException("Id was null."),
            location = "test location.",
            details = mapOf("test" to "test")
        )

        assertIs<Info>(infoError)
        assertIs<Report>(error)
        println(infoError.stackTraceToString())
        println()
        println(error.stackTraceToString())
        println()
        println(error.cause?.stackTraceToString())
        println()
        val regressionError = error.regression(
            cause = NullPointerException("Id 2 was null."),
            location = "test location 2.",
            details = mapOf("test 2" to "test 2")
        )
        println(regressionError.stackTraceToString())
        println()
        println(regressionError.exceptionLocation())
    }
}
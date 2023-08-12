package io.github.elkhoudiry.klogger.core.shared.errors

import io.github.elkhoudiry.klogger.core.shared.message.message
import io.github.elkhoudiry.klogger.core.shared.platform.Platform.exceptionLocation
import kotlin.test.Test
import kotlin.test.assertIs

class ThrowTest {

    @Test
    fun testClass() {
        val infoError = InfoThrow(message = message("test message."))
        val error = CriticalThrow(
            message = message("test message."),
            cause = NullPointerException("Id was null."),
            callLocation = "test location.",
            details = mapOf("test" to "test")
        )

        assertIs<InfoThrow>(infoError)
        assertIs<CriticalThrow>(error)
        println(infoError.stackTraceToString())
        println()
        println(error.stackTraceToString())
        println()
        println(error.cause?.stackTraceToString())
        println()
        val regressionError = error.regression(
            message = message("test message 2."),
            cause = NullPointerException("Id 2 was null."),
            location = "test location 2.",
            details = mapOf("test 2" to "test 2")
        )
        println(regressionError.stackTraceToString())
        println()
        println(regressionError.exceptionLocation())
    }
}
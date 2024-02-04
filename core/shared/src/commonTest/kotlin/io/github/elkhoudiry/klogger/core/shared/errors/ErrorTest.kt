package io.github.elkhoudiry.klogger.core.shared.errors

import io.github.elkhoudiry.klogger.core.shared.message.message
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
    }
}
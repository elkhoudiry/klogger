package io.github.elkhoudiry.klogger.core.shared.platform

import kotlin.test.Test

class PlatformTest {

    @Test
    fun testCallLocation() {
        println("[LOG] ${Platform.executeLocation()}")
    }
}
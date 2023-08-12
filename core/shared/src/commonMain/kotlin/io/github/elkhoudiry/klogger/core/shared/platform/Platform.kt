package io.github.elkhoudiry.klogger.core.shared.platform

expect object Platform {

    inline fun executeLocation(): String

    fun Throwable.exceptionLocation(): String

    fun Exception.exceptionLocation(): String
}

val Platform.executeLocationList
    get() = listOf("io.github.elkhoudiry")
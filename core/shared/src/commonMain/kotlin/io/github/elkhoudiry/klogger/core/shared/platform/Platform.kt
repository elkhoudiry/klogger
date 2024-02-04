package io.github.elkhoudiry.klogger.core.shared.platform

import com.benasher44.uuid.uuid4

expect object Platform {

    inline fun executeLocation(): String

    fun Throwable.exceptionLocation(): String

    fun Exception.exceptionLocation(): String
}

val Platform.executeLocationList
    get() = listOf("io.github.elkhoudiry")

fun uuid(): String = uuid4().toString().replace("-", "")
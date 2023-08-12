package io.github.elkhoudiry.klogger.core.shared.platform

actual object Platform {

    @Suppress("NOTHING_TO_INLINE")
    actual inline fun executeLocation(): String {
        return Throwable().getStackTrace().getExceptionLocation()
    }

    actual fun Throwable.exceptionLocation(): String {
        return getStackTrace().getExceptionLocation()
    }

    actual fun Exception.exceptionLocation(): String {
        return getStackTrace().getExceptionLocation()
    }

    fun Array<String>.getExceptionLocation(): String {
        val trace = this
        var executionStringMatch = ""
        val line = trace.firstOrNull { element ->
            Platform.executeLocationList.any { match ->
                element.contains(match).also { if (it) executionStringMatch = match }
            }
        } ?: trace[2]
        return line
            .replaceAfter("+", "")
            .replaceAfterLast("{}", "")
            .trimEnd(' ', '+', '{', '}')
            .replaceBefore(executionStringMatch, "")
            .replace(Regex("\\(.*\\)"), "()")
    }
}
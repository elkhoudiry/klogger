package io.github.elkhoudiry.klogger.core.shared.platform

actual object Platform {

    @Suppress("NOTHING_TO_INLINE")
    actual inline fun executeLocation(): String {
        return Thread.currentThread().stackTrace.getExceptionLocation()
    }

    actual fun Throwable.exceptionLocation(): String {
        return stackTrace.getExceptionLocation()
    }

    actual fun Exception.exceptionLocation(): String {
        return stackTrace.getExceptionLocation()
    }

    private fun StackTraceElement.getCorrectMethodName(): String {
        val stringRepresentation = this.toString()
        if (stringRepresentation.contains("invokeSuspend")) {
            val split = stringRepresentation.split("$")
            return if (split.size >= 2) split[1] else methodName
        }

        return methodName
    }

    fun Array<out StackTraceElement>.getExceptionLocation(): String {
        val filtered = filter { !it.className.contains("$") }
        val result = filtered.getElement()
        val trace = result ?: if (size >= 3) {
            this[2]
        } else if (this.isNotEmpty()) {
            this.last()
        } else {
            return "No trace found"
        }
        val className = if (trace.className.contains("$")) trace.className.split("$").first() else trace.className
        val method = trace.getCorrectMethodName()

        return "${className.split(".").last()} -> $method() @ ${trace.lineNumber}"
    }

    private fun List<StackTraceElement>.getElement(): StackTraceElement? {
        val first = firstOrNull { element -> element.condition() }
        val index = indexOf(first)
        val result = if (first != null && index != -1 && size > index && this[index + 1].condition()) {
            this[index + 1]
        } else {
            first
        }

        return result
    }

    private fun StackTraceElement.condition(): Boolean {
        return Platform.executeLocationList.any { className.startsWith(it) } && !toString().contains(
            "invokeSuspend"
        )
    }
}
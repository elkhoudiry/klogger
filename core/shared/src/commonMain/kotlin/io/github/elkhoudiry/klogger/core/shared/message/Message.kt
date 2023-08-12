package io.github.elkhoudiry.klogger.core.shared.message

data class Message(val en: String) {
    fun get(): String {
        return en
    }
}

fun message(en: String?): Message {
    return Message(en ?: "No message available.")
}

fun String?.toMessage(en: String? = this): Message {
    return message(en)
}
package io.github.elkhoudiry.klogger.core.shared.message

class Message(message: String, vararg locales: Pair<MessageLocalization, String>) {

    private val value: Map<MessageLocalization, String> = mapOf(Default to message) + locales

    fun get(locale: MessageLocalization = Default): String {
        return value[locale] ?: value[Default]!!
    }
}

fun message(default: String): Message {
    return Message(default)
}

interface MessageLocalization

object Default : MessageLocalization
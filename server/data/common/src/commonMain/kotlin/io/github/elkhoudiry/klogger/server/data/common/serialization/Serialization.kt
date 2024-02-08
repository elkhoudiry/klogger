package io.github.elkhoudiry.klogger.server.data.common.serialization

import io.github.elkhoudiry.klogger.core.shared.serialization.stringOrNull
import io.github.elkhoudiry.klogger.server.data.common.models.badRequest
import io.github.elkhoudiry.klogger.server.data.common.models.missingProperty
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import kotlin.reflect.KClass

val JsonElement.arrayOrNull
    get() = try {
        jsonArray
    } catch (ex: IllegalArgumentException) {
        null
    }

val JsonElement.objectOrNull
    get() = try {
        jsonObject
    } catch (ex: IllegalArgumentException) {
        null
    }

fun JsonObject.stringOrReject(key: String): String {
    return (get(key) ?: missingProperty(key)).jsonPrimitive.stringOrNull ?: badRequest("Invalid $key")
}

fun <T : Enum<T>> JsonObject.stringOrReject(key: String, enum: KClass<T>): T {
    val value = stringOrReject(key)
    return enum.java.enumConstants.firstOrNull { it.name.equals(value, true) }
        ?: badRequest("Invalid $key")
}

fun JsonObject.intOrReject(key: String): Int {
    return (get(key) ?: missingProperty(key)).jsonPrimitive.intOrNull ?: badRequest("Invalid $key")
}

fun JsonObject.longOrReject(key: String): Long {
    return (get(key) ?: missingProperty(key)).jsonPrimitive.longOrNull ?: badRequest("Invalid $key")
}

fun JsonObject.doubleOrReject(key: String): Double {
    return (get(key) ?: missingProperty(key)).jsonPrimitive.doubleOrNull ?: badRequest("Invalid $key")
}

fun JsonObject.booleanOrReject(key: String): Boolean {
    return (get(key) ?: missingProperty(key)).jsonPrimitive.booleanOrNull ?: badRequest("Invalid $key")
}
package io.github.elkhoudiry.klogger.core.shared.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}

fun Json.parse(text: String): JsonElement? {
    return try {
        parseToJsonElement(text)
    } catch (ex: SerializationException) {
        null
    } catch (ex: Exception) {
        throw ex
    }
}

fun Json.parseObject(text: String): JsonObject? {
    return try {
        parseToJsonElement(text).jsonObject
    } catch (ex: SerializationException) {
        null
    } catch (ex: IllegalArgumentException) {
        null
    } catch (ex: Exception) {
        throw ex
    }
}

fun Json.parseArray(text: String): JsonArray? {
    return try {
        parseToJsonElement(text).jsonArray
    } catch (ex: SerializationException) {
        null
    } catch (ex: IllegalArgumentException) {
        null
    } catch (ex: Exception) {
        throw ex
    }
}

val JsonPrimitive.stringOrNull get() = if (isString) contentOrNull else null
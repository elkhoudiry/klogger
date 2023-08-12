package io.github.elkhoudiry.klogger.core.shared.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}
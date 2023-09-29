package io.github.elkhoudiry.klogger.client.data.logger

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

class KtorHttpClient(
    engine: HttpClientEngine,
    unsafeEngine: HttpClientEngine
) {

    val client = io.ktor.client.HttpClient(engine) {
        install(ContentNegotiation) {
            json(io.github.elkhoudiry.klogger.core.shared.serialization.json)
        }
        install(ContentEncoding) {
            deflate()
            gzip()
        }
        expectSuccess = true
    }
}
package io.github.elkhoudiry.klogger.server.application

import io.github.elkhoudiry.klogger.server.application.plugins.configureHTTP
import io.github.elkhoudiry.klogger.server.application.plugins.configureMonitoring
import io.github.elkhoudiry.klogger.server.application.plugins.configureRouting
import io.github.elkhoudiry.klogger.server.application.plugins.configureSerialization
import io.github.elkhoudiry.klogger.server.data.database.cassandra.CassandraDatabase
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.jetty.Jetty
import kotlinx.coroutines.runBlocking

fun main() {
    val hostname = System.getenv("SERVER_HOSTNAME") ?: "localhost"
    val port = System
        .getenv("SERVER_PORT")
        ?.toInt() ?: 8080

    val cass = CassandraDatabase()
    runBlocking { cass.connect() }

    embeddedServer(Jetty, port = port, host = hostname, module = {
        module(cass)
    }).start(wait = true)
}

fun Application.module(local: CassandraDatabase) {
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting(local)
}
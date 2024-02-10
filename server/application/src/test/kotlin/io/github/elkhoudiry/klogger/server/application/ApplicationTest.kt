package io.github.elkhoudiry.klogger.server.application

import com.github.nosan.embedded.cassandra.Cassandra
import com.github.nosan.embedded.cassandra.CassandraBuilder
import io.github.elkhoudiry.klogger.server.application.plugins.configureRouting
import io.github.elkhoudiry.klogger.server.data.database.cassandra.CassandraDatabase
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ApplicationTest {

    private lateinit var cassandra: CassandraDatabase
    private lateinit var embeddedCassandra: Cassandra

    @Before
    @Throws(Exception::class)
    fun setUp() {
        embeddedCassandra = CassandraBuilder()
            .addEnvironmentVariable("JAVA_HOME", "/usr/lib/jvm/java-8-openjdk")
            .build()
        embeddedCassandra.start()

        cassandra = CassandraDatabase(
            hostname = embeddedCassandra.settings.address.hostAddress,
            port = embeddedCassandra.settings.port,
            datacenter = "datacenter1"
        )
        runBlocking {
            cassandra.connect()
        }
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() = runBlocking {
        embeddedCassandra.stop()
        cassandra.close()
    }

    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting(cassandra)
        }
        client
            .get("/")
            .apply {
                assertEquals(HttpStatusCode.OK, status)
                assertEquals("Hello Klogger!", bodyAsText())
            }
    }
}
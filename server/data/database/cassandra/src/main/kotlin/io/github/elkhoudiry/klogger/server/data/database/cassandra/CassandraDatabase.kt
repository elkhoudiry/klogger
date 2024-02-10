package io.github.elkhoudiry.klogger.server.data.database.cassandra

import io.github.elkhoudiry.realtime.cassandra.CassandraConnector
import kotlinx.coroutines.delay
import org.cognitor.cassandra.migration.Database
import org.cognitor.cassandra.migration.MigrationRepository
import org.cognitor.cassandra.migration.MigrationTask
import kotlin.time.Duration.Companion.seconds

class CassandraDatabase(
    private val hostname: String = System.getenv("CASSANDRA_HOST") ?: "localhost",
    private val port: Int = System
        .getenv("CASSANDRA_PORT")
        ?.toInt() ?: 9042,
    private val keyspace: String = "klogger",
    private val datacenter: String = "Seed",
) {
    private val cassandra = CassandraConnector()

    suspend fun connect() {
        initCassandra()
    }

    private suspend fun initCassandra() {
        repeat(10) {
            try {
                cassandra.connect(hostname, port, datacenter)
                val database = Database(cassandra.session, keyspace)
                val migration = MigrationTask(database, MigrationRepository())
                migration.migrate()
                cassandra.connect(hostname, port, datacenter)
                return
            } catch (ex: Exception) {
                if (it == 9) throw ex
                delay(30.seconds.inWholeMilliseconds)
            }
        }
    }
}
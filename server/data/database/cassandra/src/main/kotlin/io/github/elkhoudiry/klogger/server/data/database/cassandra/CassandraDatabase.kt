package io.github.elkhoudiry.klogger.server.data.database.cassandra

import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry
import io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.LogsCassandraCache
import io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.codecs.LogPropertyCodec
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
    private val datacenter: String = "Seed",
    val keyspace: String = "klogger"
) {
    private val cassandra = CassandraConnector()
    val logs  = lazy { LogsCassandraCache(cassandra.session, keyspace) }

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
                initCodecs()

                // Important to reopen the session after the migration
                cassandra.connect(hostname, port, datacenter)
                return
            } catch (ex: Exception) {
                println("[ERROR] Cassandra connection failed: ${ex.message}")
                if (it == 9) throw ex
                delay(30.seconds.inWholeMilliseconds)
            }
        }
    }

    private fun initCodecs() {
        val type = cassandra.session.metadata
            .getKeyspace(keyspace)
            .get()
            .getUserDefinedType("log_property")
            .get()

        val codec = LogPropertyCodec(CodecRegistry.DEFAULT.codecFor(type))

        (cassandra.session.context.codecRegistry as MutableCodecRegistry).register(codec)
    }
}
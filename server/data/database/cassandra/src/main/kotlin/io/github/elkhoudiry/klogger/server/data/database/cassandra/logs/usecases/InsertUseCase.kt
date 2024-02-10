package io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.usecases

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import com.datastax.oss.driver.api.querybuilder.term.Term
import io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.codecs.LogPropertyCodec
import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext

internal class InsertUseCase(
    private val session: CqlSession,
    private val keyspace: String,
    private val table: String
) {
    private val type = session.metadata
        .getKeyspace(keyspace)
        .get()
        .getUserDefinedType("log_property")
        .get()
    private val codec = LogPropertyCodec(CodecRegistry.DEFAULT.codecFor(type))
    private val propertiesCodec = TypeCodecs.listOf(codec)

    suspend fun execute(log: LogEntity) = withContext(currentCoroutineContext() + Dispatchers.IO) {
        val query = QueryBuilder
            .insertInto(keyspace, table)
            .values(log.toMap())

        session.execute(query.build())
    }

    private fun LogEntity.toMap(): Map<String, Term> {
        return mapOf(
            "id" to QueryBuilder.literal(id),
            "log" to QueryBuilder.literal(log),
            "type" to QueryBuilder.literal(this.type),
            "level" to QueryBuilder.literal(level),
            "properties" to QueryBuilder.literal(properties, propertiesCodec),
            "server_datetime" to QueryBuilder.literal(serverDateTime),
            "client_datetime" to QueryBuilder.literal(clientDateTime)
        )
    }
}
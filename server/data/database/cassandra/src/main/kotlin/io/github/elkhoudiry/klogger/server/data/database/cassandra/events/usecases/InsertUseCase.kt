package io.github.elkhoudiry.klogger.server.data.database.cassandra.events.usecases

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import com.datastax.oss.driver.api.querybuilder.term.Term
import io.github.elkhoudiry.klogger.server.data.database.common.events.models.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext

internal class InsertUseCase(
    private val session: CqlSession,
    private val keyspace: String,
    private val table: String
) {
    private val propertiesCodec = getPropertiesCodec(session, keyspace)

    suspend fun execute(log: EventEntity) = withContext(currentCoroutineContext() + Dispatchers.IO) {
        val query = QueryBuilder
            .insertInto(keyspace, table)
            .values(log.toMap())

        session.execute(query.build())
    }

    private fun EventEntity.toMap(): Map<String, Term> {
        return mapOf(
            "id" to QueryBuilder.literal(id),
            "type" to QueryBuilder.literal(this.type),
            "description" to QueryBuilder.literal(description),
            "properties" to QueryBuilder.literal(properties, propertiesCodec),
            "server_datetime" to QueryBuilder.literal(serverDateTime),
            "client_datetime" to QueryBuilder.literal(clientDateTime)
        )
    }
}
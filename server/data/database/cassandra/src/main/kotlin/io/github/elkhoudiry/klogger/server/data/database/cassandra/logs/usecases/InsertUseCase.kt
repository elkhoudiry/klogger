package io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.usecases

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import com.datastax.oss.driver.api.querybuilder.term.Term
import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogEntity

internal class InsertUseCase(
    private val session: CqlSession,
    private val keyspace: String,
    private val table: String
) {

    suspend fun execute(log: LogEntity) {
        val query = QueryBuilder
            .insertInto(keyspace, table)
            .values(log.toMap())

        session.execute(query.build())
    }

    private fun LogEntity.toMap(): Map<String, Term> {
        return mapOf(
            "id" to QueryBuilder.literal(id),
            "log" to QueryBuilder.literal(log),
            "type" to QueryBuilder.literal(type),
            "level" to QueryBuilder.literal(level),
            "properties" to QueryBuilder.literal(properties),
            "server_datetime" to QueryBuilder.literal(serverDateTime),
            "client_datetime" to QueryBuilder.literal(clientDateTime)
        )
    }
}
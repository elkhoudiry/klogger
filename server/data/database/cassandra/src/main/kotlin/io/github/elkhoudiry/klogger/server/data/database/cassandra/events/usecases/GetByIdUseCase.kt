package io.github.elkhoudiry.klogger.server.data.database.cassandra.events.usecases

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import io.github.elkhoudiry.klogger.server.data.database.common.events.models.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext

class GetByIdUseCase(
    private val session: CqlSession,
    private val keyspace: String,
    private val table: String
) {
    private val propertiesCodec = getPropertiesCodec(session, keyspace)

    suspend fun execute(
        id: String
    ): EventEntity? = withContext(currentCoroutineContext() + Dispatchers.IO) {
        val query = QueryBuilder
            .selectFrom(keyspace, table)
            .all()
            .whereColumn("id")
            .isEqualTo(QueryBuilder.literal(id))

        val result = session.execute(query.build())

        return@withContext result
            .map { row -> row.toModel(propertiesCodec) }
            .one()
    }
}
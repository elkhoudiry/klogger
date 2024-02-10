package io.github.elkhoudiry.klogger.server.data.database.cassandra.events

import com.datastax.oss.driver.api.core.CqlSession
import io.github.elkhoudiry.klogger.server.data.database.cassandra.events.usecases.EventsCassandraUseCases
import io.github.elkhoudiry.klogger.server.data.database.common.events.EventsLocalCache
import io.github.elkhoudiry.klogger.server.data.database.common.events.models.EventEntity

class EventsCassandraCache(
    session: CqlSession,
    keyspace: String
) : EventsLocalCache {

    private val useCases = EventsCassandraUseCases(
        session = session,
        keyspace = keyspace
    )

    override suspend fun getAll(): List<EventEntity> {
        return useCases.getAll.execute()
    }

    override suspend fun getById(id: String): EventEntity? {
        return useCases.getById
            .execute(id)
    }

    override suspend fun insert(log: EventEntity) {
        useCases.insert.execute(log)
    }
}
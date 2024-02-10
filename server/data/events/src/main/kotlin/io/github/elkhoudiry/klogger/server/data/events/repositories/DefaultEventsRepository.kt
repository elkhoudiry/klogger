package io.github.elkhoudiry.klogger.server.data.events.repositories

import io.github.elkhoudiry.klogger.server.data.database.common.events.EventsLocalCache
import io.github.elkhoudiry.klogger.server.data.events.models.Event
import io.github.elkhoudiry.klogger.server.data.events.models.toEntity
import io.github.elkhoudiry.klogger.server.data.events.models.toModel

class DefaultEventsRepository(
    private val local: EventsLocalCache
) : EventsRepository {
    override suspend fun getAll(): List<Event> {
        return local
            .getAll()
            .map { it.toModel() }
    }

    override suspend fun getById(id: String): Event? {
        return local
            .getById(id)
            ?.toModel()
    }

    override suspend fun insert(log: Event) {
        local.insert(log.toEntity())
    }

    override suspend fun deleteById(id: String): Boolean {
        TODO()
    }
}
package io.github.elkhoudiry.klogger.server.data.events.repositories

import io.github.elkhoudiry.klogger.server.data.events.models.Event

class DefaultEventsRepository : EventsRepository {
    private val list = ArrayList<Event>()

    override fun getAll(): List<Event> {
        return list
    }

    override fun getById(id: String): Event? {
        return list.find { it.id == id }
    }

    override suspend fun insert(log: Event) {
        list.add(log)
    }

    override suspend fun deleteById(id: String): Boolean {
        return list.removeIf { it.id == id }
    }
}
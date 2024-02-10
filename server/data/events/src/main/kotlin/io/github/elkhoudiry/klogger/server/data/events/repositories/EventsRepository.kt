package io.github.elkhoudiry.klogger.server.data.events.repositories

import io.github.elkhoudiry.klogger.server.data.events.models.Event

interface EventsRepository {

    suspend fun getAll(): List<Event>

    suspend fun getById(id: String): Event?

    suspend fun insert(log: Event)

    suspend fun deleteById(id: String): Boolean
}
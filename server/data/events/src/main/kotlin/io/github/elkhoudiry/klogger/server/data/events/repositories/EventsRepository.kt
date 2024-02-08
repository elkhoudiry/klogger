package io.github.elkhoudiry.klogger.server.data.events.repositories

import io.github.elkhoudiry.klogger.server.data.events.models.Event

interface EventsRepository {

    fun getAll(): List<Event>

    fun getById(id: String): Event?

    suspend fun insert(log: Event)

    suspend fun deleteById(id: String): Boolean
}
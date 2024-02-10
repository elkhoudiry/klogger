package io.github.elkhoudiry.klogger.server.data.database.common.events

import io.github.elkhoudiry.klogger.server.data.database.common.events.models.EventEntity

interface EventsLocalCache {
    suspend fun getAll(): List<EventEntity>

    suspend fun getById(id: String): EventEntity?

    suspend fun insert(log: EventEntity)
}
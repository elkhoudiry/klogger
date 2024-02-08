package io.github.elkhoudiry.server.data.logger.repositories

import io.github.elkhoudiry.server.data.logger.models.Log

interface LoggerRepository {

    fun getAll(): List<Log>

    fun getById(id: String): Log?

    suspend fun insert(log: Log)

    suspend fun deleteById(id: String): Boolean
}
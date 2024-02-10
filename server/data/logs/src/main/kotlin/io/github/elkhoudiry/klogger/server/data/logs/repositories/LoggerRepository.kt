package io.github.elkhoudiry.klogger.server.data.logs.repositories

import io.github.elkhoudiry.klogger.server.data.logs.models.Log

interface LoggerRepository {

    suspend fun getAll(): List<Log>

    suspend fun getById(id: String): Log?

    suspend fun insert(log: Log)

    suspend fun deleteById(id: String): Boolean
}
package io.github.elkhoudiry.klogger.server.data.logs.repositories

import io.github.elkhoudiry.klogger.server.data.logs.models.Log

interface LoggerRepository {

    fun getAll(): List<Log>

    fun getById(id: String): Log?

    suspend fun insert(log: Log)

    suspend fun deleteById(id: String): Boolean
}
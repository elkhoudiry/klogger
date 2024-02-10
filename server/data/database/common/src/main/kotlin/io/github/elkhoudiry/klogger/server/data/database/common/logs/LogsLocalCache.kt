package io.github.elkhoudiry.klogger.server.data.database.common.logs

import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogEntity

interface LogsLocalCache {
    suspend fun getAll(): List<LogEntity>

    suspend fun getById(id: String): LogEntity?

    suspend fun insert(log: LogEntity)
}
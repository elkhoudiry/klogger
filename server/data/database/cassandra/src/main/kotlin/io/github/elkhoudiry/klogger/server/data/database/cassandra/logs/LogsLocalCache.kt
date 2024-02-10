package io.github.elkhoudiry.klogger.server.data.database.cassandra.logs

import io.github.elkhoudiry.klogger.server.data.database.common.logs.LogsLocalCache
import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogEntity

class LogsCassandraCache : LogsLocalCache {
    override suspend fun getAll(): List<LogEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: String): LogEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun insert(log: LogEntity) {
        TODO("Not yet implemented")
    }
}
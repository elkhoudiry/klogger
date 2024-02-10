package io.github.elkhoudiry.klogger.server.data.logs.repositories

import io.github.elkhoudiry.klogger.server.data.database.common.logs.LogsLocalCache
import io.github.elkhoudiry.klogger.server.data.logs.models.Log
import io.github.elkhoudiry.klogger.server.data.logs.models.toLog
import io.github.elkhoudiry.klogger.server.data.logs.models.toLogEntity

class DefaultLoggerRepository(
    private val localCache: Lazy<LogsLocalCache>
) : LoggerRepository {

    override suspend fun getAll(): List<Log> {
        return localCache
            .value
            .getAll()
            .map { it.toLog() }
    }

    override suspend fun getById(id: String): Log? {
        return localCache
            .value
            .getById(id)
            ?.toLog()
    }

    override suspend fun insert(log: Log) {
        localCache.value.insert(log.toLogEntity())
    }

    override suspend fun deleteById(id: String): Boolean {
        return false
    }
}
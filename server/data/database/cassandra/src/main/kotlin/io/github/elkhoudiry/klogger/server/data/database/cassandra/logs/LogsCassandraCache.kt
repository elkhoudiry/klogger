package io.github.elkhoudiry.klogger.server.data.database.cassandra.logs

import com.datastax.oss.driver.api.core.CqlSession
import io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.usecases.LogsCassandraUseCases
import io.github.elkhoudiry.klogger.server.data.database.common.logs.LogsLocalCache
import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogEntity

class LogsCassandraCache(
    session: CqlSession,
    keyspace: String
) : LogsLocalCache {

    private val useCases = LogsCassandraUseCases(
        session = session,
        keyspace = keyspace
    )

    override suspend fun getAll(): List<LogEntity> {
        return useCases.getAll.execute()
    }

    override suspend fun getById(id: String): LogEntity? {
        return useCases.getById
            .execute(id)
    }

    override suspend fun insert(log: LogEntity) {
        useCases.insert.execute(log)
    }
}
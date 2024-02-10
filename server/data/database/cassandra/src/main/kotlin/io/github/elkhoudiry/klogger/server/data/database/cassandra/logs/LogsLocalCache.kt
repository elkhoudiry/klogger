package io.github.elkhoudiry.klogger.server.data.database.cassandra.logs

import com.datastax.oss.driver.api.core.CqlSession
import io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.usecases.LogsCassandraUseCases
import io.github.elkhoudiry.klogger.server.data.database.common.logs.LogsLocalCache
import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogEntity

class LogsCassandraCache(
    private val session: CqlSession,
    private val keyspace: String
) : LogsLocalCache {

    private val useCases = LogsCassandraUseCases(
        session = session,
        keyspace = keyspace
    )

    override suspend fun getAll(): List<LogEntity> {
        return useCases.getAll.execute()
    }

    override suspend fun getById(id: String): LogEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun insert(log: LogEntity) {
        useCases.insert.execute(log)
    }
}
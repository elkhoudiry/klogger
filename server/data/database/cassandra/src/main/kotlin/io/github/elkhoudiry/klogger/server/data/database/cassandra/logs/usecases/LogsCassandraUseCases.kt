package io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.usecases

import com.datastax.oss.driver.api.core.CqlSession

internal class LogsCassandraUseCases(
    session: CqlSession,
    keyspace: String
) {
    val getAll = GetAllUseCase(session = session, keyspace = keyspace, table = "logs")

    val insert = InsertUseCase(session, keyspace = keyspace, table = "logs")
}
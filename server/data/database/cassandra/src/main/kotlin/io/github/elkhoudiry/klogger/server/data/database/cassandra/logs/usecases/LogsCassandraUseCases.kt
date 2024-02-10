package io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.usecases

import com.datastax.oss.driver.api.core.CqlSession

internal class LogsCassandraUseCases(
    session: CqlSession,
    keyspace: String
) {
    val getAll = GetAllUseCase(session = session, keyspace = keyspace, table = "log")

    val getById = GetByIdUseCase(session = session, keyspace = keyspace, table = "log")

    val insert = InsertUseCase(session, keyspace = keyspace, table = "log")
}
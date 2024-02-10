package io.github.elkhoudiry.klogger.server.data.database.cassandra.events.usecases

import com.datastax.oss.driver.api.core.CqlSession

internal class EventsCassandraUseCases(
    session: CqlSession,
    keyspace: String
) {
    val getAll = GetAllUseCase(session = session, keyspace = keyspace, table = "event")

    val getById = GetByIdUseCase(session = session, keyspace = keyspace, table = "event")

    val insert = InsertUseCase(session, keyspace = keyspace, table = "event")
}
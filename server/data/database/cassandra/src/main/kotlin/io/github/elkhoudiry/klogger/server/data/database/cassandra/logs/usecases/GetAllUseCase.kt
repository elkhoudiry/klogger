package io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.usecases

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogEntity
import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogPropertyEntity

internal class GetAllUseCase(
    private val session: CqlSession,
    private val keyspace: String,
    private val table: String,
) {

    suspend fun execute(): List<LogEntity> {
        val query = QueryBuilder
            .selectFrom(keyspace, table)
            .all()

        val result = session.execute(query.build())

        return result
            .map { row -> row.toModel() }
            .all()
    }

    private fun Row.toModel(): LogEntity {
        return LogEntity(
            id = getString("id")!!,
            log = getString("log")!!,
            type = getString("type")!!,
            level = getString("level")!!,
            properties = getList("properties", LogPropertyEntity::class.java)!!,
            serverDateTime = getString("server_datetime")!!,
            clientDateTime = getString("client_datetime")!!
        )
    }
}
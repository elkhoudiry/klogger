package io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.usecases

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.type.codec.TypeCodec
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry
import io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.codecs.LogPropertyCodec
import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogEntity
import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogPropertyEntity

internal fun getPropertiesCodec(session: CqlSession, keyspace: String): TypeCodec<List<LogPropertyEntity>> {
    val type = session.metadata
        .getKeyspace(keyspace)
        .get()
        .getUserDefinedType("log_property")
        .get()
    val codec = LogPropertyCodec(CodecRegistry.DEFAULT.codecFor(type))
    return TypeCodecs.listOf(codec)
}

internal fun Row.toModel(propertiesCodec: TypeCodec<List<LogPropertyEntity>>): LogEntity {
    return LogEntity(
        id = getString("id")!!,
        log = getString("log")!!,
        type = getString("type")!!,
        level = getString("level")!!,
        properties = get("properties", propertiesCodec)!!,
        serverDateTime = getString("server_datetime")!!,
        clientDateTime = getString("client_datetime")!!
    )
}
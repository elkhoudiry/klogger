package io.github.elkhoudiry.klogger.server.data.database.cassandra.events.usecases

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.type.codec.TypeCodec
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry
import io.github.elkhoudiry.klogger.server.data.database.cassandra.events.codecs.EventPropertyCodec
import io.github.elkhoudiry.klogger.server.data.database.common.events.models.EventEntity
import io.github.elkhoudiry.klogger.server.data.database.common.events.models.EventPropertyEntity

internal fun getPropertiesCodec(session: CqlSession, keyspace: String): TypeCodec<List<EventPropertyEntity>> {
    val type = session.metadata
        .getKeyspace(keyspace)
        .get()
        .getUserDefinedType("event_property")
        .get()
    val codec = EventPropertyCodec(CodecRegistry.DEFAULT.codecFor(type))
    return TypeCodecs.listOf(codec)
}

internal fun Row.toModel(propertiesCodec: TypeCodec<List<EventPropertyEntity>>): EventEntity {
    return EventEntity(
        id = getString("id")!!,
        type = getString("type")!!,
        description = getString("description")!!,
        properties = get("properties", propertiesCodec)!!,
        serverDateTime = getString("server_datetime")!!,
        clientDateTime = getString("client_datetime")!!
    )
}
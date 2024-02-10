package io.github.elkhoudiry.klogger.server.data.database.cassandra.events.codecs

import com.datastax.oss.driver.api.core.ProtocolVersion
import com.datastax.oss.driver.api.core.data.UdtValue
import com.datastax.oss.driver.api.core.type.DataType
import com.datastax.oss.driver.api.core.type.UserDefinedType
import com.datastax.oss.driver.api.core.type.codec.TypeCodec
import com.datastax.oss.driver.api.core.type.reflect.GenericType
import io.github.elkhoudiry.klogger.server.data.database.common.events.models.EventPropertyEntity
import java.nio.ByteBuffer

class EventPropertyCodec(private val innerCodec: TypeCodec<UdtValue>) : TypeCodec<EventPropertyEntity> {

    private val userType: UserDefinedType = innerCodec.cqlType as UserDefinedType

    override fun getJavaType(): GenericType<EventPropertyEntity> {
        return GenericType.of(EventPropertyEntity::class.java)
    }

    override fun getCqlType(): DataType {
        return userType
    }

    override fun decode(bytes: ByteBuffer?, protocolVersion: ProtocolVersion): EventPropertyEntity? {
        return toAddress(innerCodec.decode(bytes, protocolVersion))
    }

    override fun encode(value: EventPropertyEntity?, protocolVersion: ProtocolVersion): ByteBuffer? {
        return innerCodec.encode(toUDTValue(value), protocolVersion)
    }

    override fun parse(value: String?): EventPropertyEntity? {
        if (value.isNullOrEmpty()) return null

        return toAddress(innerCodec.parse(value))
    }

    override fun format(value: EventPropertyEntity?): String {
        if (value == null) return "NULL"

        return innerCodec.format(toUDTValue(value))
    }

    private fun toAddress(value: UdtValue?): EventPropertyEntity? {
        if (value == null) return null
        return EventPropertyEntity(
            id = value.getString("id")!!,
            name = value.getString("name")!!,
            value = value.getString("value")!!
        )
    }

    private fun toUDTValue(value: EventPropertyEntity?): UdtValue? {
        if (value == null) return null

        return userType
            .newValue()
            .setString("id", value.id)
            .setString("name", value.name)
            .setString("value", value.value)
    }
}
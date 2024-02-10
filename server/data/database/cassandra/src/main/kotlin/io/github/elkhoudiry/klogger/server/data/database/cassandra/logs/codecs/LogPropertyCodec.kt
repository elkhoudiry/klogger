package io.github.elkhoudiry.klogger.server.data.database.cassandra.logs.codecs

import com.datastax.oss.driver.api.core.ProtocolVersion
import com.datastax.oss.driver.api.core.data.UdtValue
import com.datastax.oss.driver.api.core.type.DataType
import com.datastax.oss.driver.api.core.type.UserDefinedType
import com.datastax.oss.driver.api.core.type.codec.TypeCodec
import com.datastax.oss.driver.api.core.type.reflect.GenericType
import io.github.elkhoudiry.klogger.server.data.database.common.logs.models.LogPropertyEntity
import java.nio.ByteBuffer

class LogPropertyCodec(private val innerCodec: TypeCodec<UdtValue>) : TypeCodec<LogPropertyEntity> {

    private val userType: UserDefinedType = innerCodec.cqlType as UserDefinedType

    override fun getJavaType(): GenericType<LogPropertyEntity> {
        return GenericType.of(LogPropertyEntity::class.java)
    }

    override fun getCqlType(): DataType {
        return userType
    }

    override fun decode(bytes: ByteBuffer?, protocolVersion: ProtocolVersion): LogPropertyEntity? {
        return toAddress(innerCodec.decode(bytes, protocolVersion))
    }


    override fun encode(value: LogPropertyEntity?, protocolVersion: ProtocolVersion): ByteBuffer? {
        return innerCodec.encode(toUDTValue(value), protocolVersion)
    }

    override fun parse(value: String?): LogPropertyEntity? {
        if (value.isNullOrEmpty()) return null

        return toAddress(innerCodec.parse(value))
    }

    override fun format(value: LogPropertyEntity?): String {
        if (value == null) return "NULL"

        return innerCodec.format(toUDTValue(value))
    }

    private fun toAddress(value: UdtValue?): LogPropertyEntity? {
        if (value == null) return null
        return LogPropertyEntity(
            id = value.getString("id")!!,
            name = value.getString("name")!!,
            value = value.getString("value")!!
        )
    }

    private fun toUDTValue(value: LogPropertyEntity?): UdtValue? {
        if (value == null) return null

        return userType
            .newValue()
            .setString("id", value.id)
            .setString("name", value.name)
            .setString("value", value.value)
    }
}
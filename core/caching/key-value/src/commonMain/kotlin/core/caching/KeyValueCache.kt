package core.caching

import kotlinx.coroutines.flow.Flow

interface KeyValueCache {

    fun setString(key: String, value: String)

    fun setInt(key: String, value: Int)

    fun setLong(key: String, value: Long)

    fun setBoolean(key: String, value: Boolean)

    fun setFloat(key: String, value: Float)

    fun getString(key: String, default: String): String

    fun streamString(key: String, default: String): Flow<String>

    fun getInt(key: String, default: Int): Int

    fun streamInt(key: String, default: Int): Flow<Int>

    fun getLong(key: String, default: Long): Long

    fun streamFloat(key: String, default: Float): Flow<Float>

    fun getBoolean(key: String, default: Boolean): Boolean

    fun streamBoolean(key: String, default: Boolean): Flow<Boolean>

    fun getFloat(key: String, default: Float): Float

    fun streamLong(key: String, default: Long): Flow<Long>

    fun remove(key: String)
}
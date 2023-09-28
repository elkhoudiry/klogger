package core.caching

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import platform.Foundation.NSUserDefaults

private val listeners = mutableMapOf<String, Listener>()

class IosKeyValueCache : KeyValueCache {

    override fun setString(key: String, value: String) {
        NSUserDefaults.standardUserDefaults.setObject(value, key)
    }

    override fun setInt(key: String, value: Int) {
        NSUserDefaults.standardUserDefaults.setInteger(value.toLong(), key)
    }

    override fun setLong(key: String, value: Long) {
        NSUserDefaults.standardUserDefaults.setInteger(value, key)
    }

    override fun setBoolean(key: String, value: Boolean) {
        NSUserDefaults.standardUserDefaults.setBool(value, key)
    }

    override fun setFloat(key: String, value: Float)  {
        NSUserDefaults.standardUserDefaults.setFloat(value, key)
    }

    override fun getString(key: String, default: String): String  {
        val value = NSUserDefaults.standardUserDefaults.objectForKey(key) ?: default
        val result = if (value !is String || value.isBlank()) default else value

        return result
    }

    override fun streamString(key: String, default: String): Flow<String> {
        val listener = if (listeners[key] != null) {
            listeners[key]!!
        } else {
            val new = Listener(key, default)
            listeners[key] = new
            new
        }

        return listener.stream.map {
            val value = (it).toString()
            value
        }
    }

    override fun getInt(key: String, default: Int): Int  {
        val value = NSUserDefaults.standardUserDefaults.integerForKey(key).toInt()
        val result = if (value == 0) default else value

        return result
    }

    override fun streamInt(key: String, default: Int): Flow<Int> {
        val listener = if (listeners[key] != null) {
            listeners[key]!!
        } else {
            val new = Listener(key, default)
            listeners[key] = new
            new
        }

        return listener.stream.map {
            val value = (it).toString().toInt()
            value
        }
    }

    override fun getLong(key: String, default: Long): Long  {
        val value = NSUserDefaults.standardUserDefaults.integerForKey(key)
        val result = if (value == 0L) default else value

        return result
    }

    override fun streamLong(key: String, default: Long): Flow<Long> {
        val listener = if (listeners[key] != null) {
            listeners[key]!!
        } else {
            val new = Listener(key, default)
            listeners[key] = new
            new
        }

        return listener.stream.map {
            val value = (it).toString().toLong()
            value
        }
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        val result = NSUserDefaults.standardUserDefaults.boolForKey(key)

        return result
    }

    override fun streamBoolean(key: String, default: Boolean): Flow<Boolean> {
        val listener = if (listeners[key] != null) {
            listeners[key]!!
        } else {
            val new = Listener(key, default)
            listeners[key] = new
            new
        }

        return listener.stream.map {
            val value = (it).toString().toBoolean()
            value
        }
    }

    override fun getFloat(key: String, default: Float): Float  {
        val value = NSUserDefaults.standardUserDefaults.floatForKey(key)
        val result = if (value == 0.0f) default else value

        return result
    }

    override fun streamFloat(key: String, default: Float): Flow<Float>  {
        val listener = if (listeners[key] != null) {
            listeners[key]!!
        } else {
            val new = Listener(key, default)
            listeners[key] = new
            new
        }

        return listener.stream.map {
            val value = (it).toString().toFloat()
            value
        }
    }

    override fun remove(key: String)  {
        NSUserDefaults.standardUserDefaults.removeObjectForKey(key)
    }
}
package core.caching

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class AndroidKeyValueCache(
    applicationContext: Context,
) : KeyValueCache {

    private val storage = applicationContext.getSharedPreferences(
        "${applicationContext.packageName}.KeyValueStorage",
        Context.MODE_PRIVATE
    )

    override fun setString(key: String, value: String) {
        storage.edit().putString(key, value).apply()
    }

    override fun setInt(key: String, value: Int) {
        storage.edit().putInt(key, value).apply()
    }

    override fun setLong(key: String, value: Long) {
        storage.edit().putLong(key, value).apply()
    }

    override fun setBoolean(key: String, value: Boolean) {
        storage.edit().putBoolean(key, value).apply()
    }

    override fun setFloat(key: String, value: Float) {
        storage.edit().putFloat(key, value).apply()
    }

    override fun getString(key: String, default: String): String {
        val result = storage.getString(key, default) ?: default
        return result
    }

    override fun streamString(key: String, default: String): Flow<String>  {
        return stream(key) {
            val value = it.getString(key, default) ?: default
            value
        }
    }

    override fun getInt(key: String, default: Int): Int {
        val result = storage.getInt(key, default)
        return result
    }

    override fun streamInt(key: String, default: Int): Flow<Int> {
        return stream(key) {
            val value = it.getInt(key, default)
            value
        }
    }

    override fun getLong(key: String, default: Long): Long  {
        val result = storage.getLong(key, default)
        return result
    }

    override fun streamLong(key: String, default: Long): Flow<Long> {
        return stream(key) {
            val value = it.getLong(key, default)
            value
        }
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        val result = storage.getBoolean(key, default)
        return result
    }

    override fun streamBoolean(key: String, default: Boolean): Flow<Boolean>  {
        return stream(key) {
            val value = it.getBoolean(key, default)
            value
        }
    }

    override fun getFloat(key: String, default: Float): Float  {
        val result = storage.getFloat(key, default)
        return result
    }

    override fun streamFloat(key: String, default: Float): Flow<Float> {
        return stream(key) {
            val value = it.getFloat(key, default)
            value
        }
    }

    override fun remove(key: String)  {
        storage.edit().remove(key).apply()
    }

    private fun <T> stream(key: String, callback: (SharedPreferences) -> T): Flow<T> {
        val flow = MutableStateFlow(callback(storage))
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, preferenceKey ->
            if (preferenceKey === key) {
                flow.update { callback(sharedPreferences) }
            }
        }

        return flow
            .onStart { storage.registerOnSharedPreferenceChangeListener(listener) }
            .onCompletion { storage.unregisterOnSharedPreferenceChangeListener(listener) }
            .distinctUntilChanged()
    }
}
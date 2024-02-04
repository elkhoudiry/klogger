package core.caching

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AndroidKeyValueCacheTest {

    private lateinit var context: Context
    private lateinit var sample: AndroidKeyValueCache

    private val name = "test-key"

    @BeforeTest
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sample = AndroidKeyValueCache(context)
    }

    @AfterTest
    fun finish() {
        sample.remove(name)
    }

    @Test
    fun setString() {
        sample.setString(name, "test123")
        assertEquals("test123", sample.getString(name, ""))
    }

    @Test
    fun setInt() {
        sample.setInt(name, 123)
        assertEquals(123, sample.getInt(name, 0))
    }

    @Test
    fun setLong() {
        sample.setLong(name, 123L)
        assertEquals(123L, sample.getLong(name, 0))
    }

    @Test
    fun setBoolean() {
        sample.setBoolean(name, true)
        assertEquals(true, sample.getBoolean(name, false))
    }

    @Test
    fun setFloat() {
        sample.setFloat(name, 123.5F)
        assertEquals(123.5F, sample.getFloat(name, 0.0F))
    }

    @Test
    fun remove() {
        sample.setFloat(name, 123.5F)
        assertEquals(123.5F, sample.getFloat(name, 0.0F))
        sample.remove(name)
        assertEquals(0.0F, sample.getFloat(name, 0.0F))
    }
}
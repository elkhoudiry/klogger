package core.caching

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class IosKeyValueCacheTest {

    private val cache = IosKeyValueCache()
    private val key = "test_value"

    @BeforeTest
    fun setup() {
        cache.remove(key)
    }

    @Test
    fun stringTest() {
        val (test, default) = listOf("hello hello", "none")

        assertEquals(default, cache.getString(key, default))
        cache.setString(key, test)
        assertEquals(test, cache.getString(key, default))
    }

    @Test
    fun integerTest() {
        val (test, default) = listOf(80, 5)

        assertEquals(default, cache.getInt(key, default))
        cache.setInt(key, test)
        assertEquals(test, cache.getInt(key, default))
    }

    @Test
    fun longTest() {
        val (test, default) = listOf(80L, 5L)

        assertEquals(default, cache.getLong(key, default))
        cache.setLong(key, test)
        assertEquals(test, cache.getLong(key, default))
    }

    @Test
    fun floatTest() {
        val (test, default) = listOf(23.666578f, 14.2223f)

        assertEquals(default, cache.getFloat(key, default))
        cache.setFloat(key, test)
        assertEquals(test, cache.getFloat(key, default))
    }

    @Test
    fun booleanTest() {
        val (test, default) = listOf(true, false)

        assertEquals(default, cache.getBoolean(key, default))
        cache.setBoolean(key, test)
        assertEquals(test, cache.getBoolean(key, default))
    }

    @Test
    fun removeTest() {
        val (test, default) = listOf(true, false)

        assertEquals(default, cache.getBoolean(key, default))
        cache.setBoolean(key, test)
        assertEquals(test, cache.getBoolean(key, default))

        cache.remove(key)
        assertEquals(default, cache.getBoolean(key, default))
    }
}
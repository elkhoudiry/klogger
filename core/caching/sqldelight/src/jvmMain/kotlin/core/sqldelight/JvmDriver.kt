package core.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.gitub.elkhoudiry.klogger.core.sqldelight.DriverFactory

class JvmDriver(
    private val baseDirectoryPath: String
) : DriverFactory {

    override fun get(schema: SqlSchema, name: String): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:$baseDirectoryPath/$name")
        schema.create(driver)
        return driver
    }
}
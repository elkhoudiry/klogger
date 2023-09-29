package core.sqldelight

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.gitub.elkhoudiry.klogger.core.sqldelight.DriverFactory

class AndroidDriverFactory(
    private val applicationContext: Context
) : DriverFactory {
    override fun get(schema: SqlSchema, name: String): SqlDriver {
        return AndroidSqliteDriver(schema, applicationContext, name)
    }
}
package io.gitub.elkhoudiry.klogger.core.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

interface DriverFactory {
    fun get(schema: SqlSchema, name: String): SqlDriver
}
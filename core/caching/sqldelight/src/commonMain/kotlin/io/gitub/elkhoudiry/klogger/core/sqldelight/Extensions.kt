package io.gitub.elkhoudiry.klogger.core.sqldelight

import app.cash.sqldelight.async.coroutines.await
import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.async.coroutines.awaitMigrate
import app.cash.sqldelight.async.coroutines.awaitQuery
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

suspend fun SqlSchema.createOrMigrate(driver: SqlDriver) {
    val currentVersion = getCurrentMetadataVersion(driver)

    if (currentVersion == 0L) createDatabase(driver) else migrateDatabase(driver, currentVersion.toInt())
}

private suspend fun SqlSchema.createDatabase(driver: SqlDriver) {
    awaitCreate(driver)
    driver.await(null, "CREATE TABLE IF NOT EXISTS DatabaseMetadata(current_version INTEGER NOT NULL);", 0)
    driver.await(null, "INSERT INTO DatabaseMetadata (current_version) VALUES ($version);", 0)
}

private suspend fun SqlSchema.migrateDatabase(driver: SqlDriver, currentVersion: Int) {
    val sql by lazy { "UPDATE DatabaseMetadata SET current_version = $version;" }
    if (currentVersion == version) return

    awaitMigrate(driver, currentVersion, version)
    driver.await(null, sql, 0)
}

private suspend fun getCurrentMetadataVersion(driver: SqlDriver) = try {
    driver.awaitQuery(
        identifier = null,
        sql = "SELECT current_version FROM DatabaseMetadata;",
        mapper = { if (it.next()) it.getLong(0) else return@awaitQuery null },
        parameters = 0
    ) ?: 0
} catch (ex: Exception) {
    0
}
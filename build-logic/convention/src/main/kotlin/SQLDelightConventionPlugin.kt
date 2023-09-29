import app.cash.sqldelight.gradle.SqlDelightExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class SQLDelightConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        pluginManager.apply(catalog.plugin("sqldelight"))
        pluginManager.apply("common.module")

        extensions.configure<SqlDelightExtension> {
            val database = this.databases.create("Database")
            database.packageName.set(namespace())
            database.verifyMigrations.set(true)
            database.generateAsync.set(true)
            database.deriveSchemaFromMigrations.set(true)
            database.schemaOutputDirectory.set(
                file("src/commonMain/sqldelight/databases")
            )
        }

        commonDependencies {
            implementation(project(":core-caching-sqldelight"))
        }
    }
}

@Suppress("unused")
fun Project.sqlDelightProjectDependency(path: String) {
    val extension by lazy { extensions.getByType<SqlDelightExtension>() }

    extension.databases.getByName("Database").dependency(project(path))
}
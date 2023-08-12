import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CommonLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val extension by lazy {
            extensions.getByType<KotlinMultiplatformExtension>()
        }
        val commonMain by lazy { extension.sourceSets.getByName("commonMain") }
        val commonTest by lazy { extension.sourceSets.getByName("commonTest") }

        pluginManager.apply("common.module")

        commonMain.dependencies {
            implementation(project(":core-shared"))
        }
    }
}
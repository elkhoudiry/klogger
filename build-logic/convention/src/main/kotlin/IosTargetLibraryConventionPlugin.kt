import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

@Suppress("unused")
class IosTargetLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val extension by lazy {
            extensions.getByType<KotlinMultiplatformExtension>()
        }
        val iosMain by lazy { extension.sourceSets.create("iosMain") }
        val iosTest by lazy { extension.sourceSets.create("iosTest") }

        pluginManager.apply("common.module")

        extension.iosX64()
        extension.iosArm64()
        extension.iosSimulatorArm64()

        iosMain.dependsOn(extension.sourceSets.getByName("commonMain"))
        extension.sourceSets.getByName("iosX64Main").dependsOn(iosMain)
        extension.sourceSets.getByName("iosArm64Main").dependsOn(iosMain)
        extension.sourceSets.getByName("iosSimulatorArm64Main").dependsOn(iosMain)

        iosTest.dependsOn(extension.sourceSets.getByName("commonTest"))
        extension.sourceSets.getByName("iosX64Test").dependsOn(iosTest)
        extension.sourceSets.getByName("iosArm64Test").dependsOn(iosTest)
        extension.sourceSets.getByName("iosSimulatorArm64Test").dependsOn(iosTest)
    }
}

@Suppress("unused")
fun Project.iosDependencies(scope: KotlinDependencyHandler.() -> Unit) {
    val extension by lazy {
        extensions.getByType<KotlinMultiplatformExtension>()
    }
    val iosMain by lazy { extension.sourceSets.getByName("iosMain") }

    iosMain.dependencies { scope() }
}

@Suppress("unused")
fun Project.iosTestDependencies(scope: KotlinDependencyHandler.() -> Unit) {
    val extension by lazy {
        extensions.getByType<KotlinMultiplatformExtension>()
    }
    val iosTest by lazy { extension.sourceSets.getByName("iosTest") }

    iosTest.dependencies { scope() }
}
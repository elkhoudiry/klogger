import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class KotlinModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply {
            apply(catalog.plugin("kotlin-jvm"))
            apply(catalog.plugin("kotlin-serialization"))
        }

        dependencies {
            add("implementation", project(":core-shared"))

            add("implementation", catalog.library("kotlinx.coroutines.core"))
            add("implementation", catalog.library("kotlinx.datetime"))
            add("implementation", catalog.library("kotlinx.serialization.json"))
            add("implementation", catalog.library("multiplatform.uuid"))


            add("testImplementation", catalog.library("kotlin.test"))
            add("testImplementation", catalog.library("kotlinx.coroutines.test"))
        }

        extensions.configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.toVersion(catalog.version("javaCompatibility").displayName)
            targetCompatibility = JavaVersion.toVersion(catalog.version("javaCompatibility").displayName)

            toolchain {
                languageVersion.set(JavaLanguageVersion.of(catalog.version("javaCompatibility").displayName))
            }
        }
    }
}
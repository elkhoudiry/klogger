import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

@Suppress("unused")
class AndroidTargetLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val extension by lazy {
            extensions.getByType<KotlinMultiplatformExtension>()
        }
        val androidMain by lazy {
            extension.sourceSets.getByName("androidMain")
        }
        val unitTest by lazy {
            extension.sourceSets.getByName("androidUnitTest")
        }
        val instrumentedTest by lazy {
            extension.sourceSets.getByName("androidInstrumentedTest")
        }
        val library by lazy { extensions.getByType<LibraryExtension>() }

        pluginManager.apply("common.module")
        pluginManager.apply("com.android.library")

        extension.android {
            compilations.all {
                kotlinOptions {
                    jvmTarget = catalog.version(name = "javaCompatibility").displayName
                }
            }
        }
        
        androidMain.dependencies {
            implementation(catalog.library("androidx.annotation"))
            implementation(catalog.library("androidx.appcompat"))
            implementation(catalog.library("androidx.core"))
        }

        unitTest.dependencies {
            implementation(catalog.library("kotlin.test"))
        }
        instrumentedTest.dependencies {
            implementation(catalog.library("androidx.test.core.ktx"))
            implementation(catalog.library("androidx.test.rules"))
            implementation(catalog.library("androidx.test.runner"))
            implementation(catalog.library("kotlin.test"))
        }

        library.compileOptions {
            sourceCompatibility = JavaVersion.toVersion(catalog.version("javaCompatibility").displayName)
            targetCompatibility = JavaVersion.toVersion(catalog.version("javaCompatibility").displayName)
        }
        library.namespace = namespace()
        library.compileSdk = catalog.version("androidCompileSdk").displayName.toInt()
        library.defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        library.defaultConfig.minSdk = catalog.version("androidMinSdk").displayName.toInt()
        library.defaultConfig.targetSdk = catalog.version("androidTargetSdk").displayName.toInt()
    }
}

@Suppress("unused")
fun Project.androidDependencies(scope: KotlinDependencyHandler.() -> Unit) {
    val extension by lazy {
        extensions.getByType<KotlinMultiplatformExtension>()
    }
    val androidMain by lazy { extension.sourceSets.getByName("androidMain") }

    androidMain.dependencies { scope() }
}

@Suppress("unused")
fun Project.androidTestDependencies(scope: KotlinDependencyHandler.() -> Unit) {
    val extension by lazy {
        extensions.getByType<KotlinMultiplatformExtension>()
    }
    val androidTest by lazy { extension.sourceSets.getByName("androidUnitTest") }

    androidTest.dependencies { scope() }
}

@Suppress("unused")
fun Project.androidInstrumentedTestDependencies(
    scope: KotlinDependencyHandler.() -> Unit
) {
    val extension by lazy {
        extensions.getByType<KotlinMultiplatformExtension>()
    }
    val androidTest by lazy { extension.sourceSets.getByName("androidInstrumentedTest") }

    androidTest.dependencies { scope() }
}
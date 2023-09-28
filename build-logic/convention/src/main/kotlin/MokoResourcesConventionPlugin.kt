
import dev.icerock.gradle.MultiplatformResourcesPluginExtension
import library
import namespace
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import plugin

class MokoResourcesConventionPlugin: Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        val kmp = extensions.getByType<KotlinMultiplatformExtension>()
        pluginManager.apply(catalog.plugin("moko.resources"))

        commonDependencies {
            api(catalog.library("moko.resources"))
//            api(catalog.library("moko.resources.compose"))
        }

        commonTestDependencies {
            implementation(catalog.library("moko.resources.test"))
        }

        extensions.configure<MultiplatformResourcesPluginExtension> {
            multiplatformResourcesPackage = namespace()
            multiplatformResourcesClassName = "Assets"
        }

        listOf(
            kmp.iosX64(),
            kmp.iosArm64(),
            kmp.iosSimulatorArm64(),
        ).forEach {
            val debug = it.binaries.findFramework(org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG)
            val release = it.binaries.findFramework(org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE)

            debug?.export(catalog.library("moko.resources"))
            debug?.export(catalog.library("moko.graphics"))
            release?.export(catalog.library("moko.resources"))
            release?.export(catalog.library("moko.graphics"))
        }

        afterEvaluate {
            tasks.getByName("runKtlintFormatOverCommonMainSourceSet").dependsOn("generateMRcommonMain")
            tasks.getByName("runKtlintCheckOverCommonMainSourceSet").dependsOn("generateMRcommonMain")
            iosTasks()
        }
    }

    private fun Project.iosTasks() {
        try {
            tasks.getByName("kspKotlinIosArm64").dependsOn("generateMRcommonMain")
            tasks.getByName("iosArm64ProcessResources").dependsOn("generateMRcommonMain")
            tasks.find{ it.name == "generateMRiosArm64Main" }?.let {
                tasks.getByName("kspKotlinIosArm64").dependsOn("generateMRiosArm64Main")
            }
            tasks.getByName("kspKotlinIosSimulatorArm64").dependsOn("generateMRcommonMain")
            tasks.getByName("iosSimulatorArm64ProcessResources").dependsOn("generateMRcommonMain")
            tasks.find { it.name == "generateMRiosSimulatorArm64Main" }?.let {
                tasks.getByName("kspKotlinIosSimulatorArm64").dependsOn("generateMRiosSimulatorArm64Main")
            }
            tasks.getByName("kspKotlinIosX64").dependsOn("generateMRcommonMain")
            tasks.getByName("iosX64ProcessResources").dependsOn("generateMRcommonMain")
            tasks.find { it.name == "generateMRiosX64Main" }?.let {
                tasks.getByName("kspKotlinIosX64").dependsOn("generateMRiosX64Main")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
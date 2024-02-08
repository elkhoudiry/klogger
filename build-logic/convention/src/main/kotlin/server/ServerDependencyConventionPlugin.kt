@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ServerDependencyConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val copyLib = tasks.create("copyLib") {
            this.doLast {
                val serverPath = "${rootProject.projectDir.path}/server/application"

                if (target.extensions.findByType<KotlinMultiplatformExtension>() != null) {
                    file("build/classes/kotlin/jvm").copyRecursively(file("$serverPath/build/classes/kotlin"), overwrite = true)
                } else {
                    file("build/classes").copyRecursively(file("$serverPath/build/classes"), overwrite = true)
                }
            }
        }

        tasks.getByName("build").finalizedBy(copyLib)
    }
}
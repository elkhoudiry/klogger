@file:Suppress("PackageDirectoryMismatch")

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ServerRouteConventionPlugin: Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("kotlin.module")
        pluginManager.apply("server.dependency.module")

        dependencies {
            add("implementation", project(":server-data-common"))

            add("implementation", catalog.library("ktor.server.core.jvm"))

            add("testImplementation", catalog.library("ktor.server.tests.jvm"))
        }
    }
}
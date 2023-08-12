pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("klogger") {
            from(files("gradle/klogger.versions.toml"))
        }
    }
}

rootProject.name = "klogger"

include(":server")

include(":core-shared")
project(":core-shared").projectDir = file("core/shared")

include(":core-server")
project(":core-server").projectDir = file("core/server")

include(":route-health")
project(":route-health").projectDir = file("server-routes/health")
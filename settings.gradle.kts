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

include(":core-caching-sqldelight")
project(":core-caching-sqldelight").projectDir = file("core/caching/sqldelight")

include(":core-caching-sqldelight-test")
project(":core-caching-sqldelight-test").projectDir = file("core/caching/sqldelight-test")

include(":core-shared")
project(":core-shared").projectDir = file("core/shared")
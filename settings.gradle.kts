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

include(":server-app")
project(":server-app").projectDir = file("server/app")

include(":server-data-logger")
project(":server-data-logger").projectDir = file("server/data/logger")

include(":core-caching-key-value")
project(":core-caching-key-value").projectDir = file("core/caching-key-value")
include(":core-caching-sqldelight")
project(":core-caching-sqldelight").projectDir = file("core/caching-sqldelight")
include(":core-server")
project(":core-server").projectDir = file("core/server")
include(":core-shared")
project(":core-shared").projectDir = file("core/shared")

include(":client-data-logger")
project(":client-data-logger").projectDir = file("client/data/logger")

include(":route-health")
project(":route-health").projectDir = file("server/routes/health")
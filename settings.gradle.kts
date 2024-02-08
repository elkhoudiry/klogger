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

include(":server-data-common")
project(":server-data-common").projectDir = file("server/data/common")
include(":server-data-events")
project(":server-data-events").projectDir = file("server/data/events")
include(":server-data-logs")
project(":server-data-logs").projectDir = file("server/data/logs")

include(":core-caching-key-value")
project(":core-caching-key-value").projectDir = file("core/caching/key-value")
include(":core-caching-sqldelight")
project(":core-caching-sqldelight").projectDir = file("core/caching/sqldelight")
include(":core-shared")
project(":core-shared").projectDir = file("core/shared")

include(":client-data-logger")
project(":client-data-logger").projectDir = file("client/data/logger")

include(":route-common")
project(":route-common").projectDir = file("server/routes/common")
include(":route-events")
project(":route-events").projectDir = file("server/routes/events")
include(":route-health")
project(":route-health").projectDir = file("server/routes/health")
include(":route-logs")
project(":route-logs").projectDir = file("server/routes/logs")
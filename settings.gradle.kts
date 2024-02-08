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

include(":core-caching-key-value")
project(":core-caching-key-value").projectDir = file("core/caching/key-value")
include(":core-caching-sqldelight")
project(":core-caching-sqldelight").projectDir = file("core/caching/sqldelight")
include(":core-shared")
project(":core-shared").projectDir = file("core/shared")

include(":client-data-logger")
project(":client-data-logger").projectDir = file("client/data/logger")

include(":server-application")
project(":server-application").projectDir = file("server/application")

include(":server-data-common")
project(":server-data-common").projectDir = file("server/data/common")
include(":server-data-events")
project(":server-data-events").projectDir = file("server/data/events")
include(":server-data-logs")
project(":server-data-logs").projectDir = file("server/data/logs")

include(":server-route-common")
project(":server-route-common").projectDir = file("server/route/common")
include(":server-route-events")
project(":server-route-events").projectDir = file("server/route/events")
include(":server-route-health")
project(":server-route-health").projectDir = file("server/route/health")
include(":server-route-logs")
project(":server-route-logs").projectDir = file("server/route/logs")
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("klogger") {
            from(files("../gradle/klogger.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")

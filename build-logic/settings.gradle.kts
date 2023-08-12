dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("klogger") {
            from(files("../gradle/klogger.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")

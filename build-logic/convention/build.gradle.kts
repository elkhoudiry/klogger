plugins {
    `kotlin-dsl`
}

group = "io.github.elkhoudiry.klogger.build.logic"

java {
    sourceCompatibility = JavaVersion.toVersion(klogger.versions.javaCompatibility.get())
    targetCompatibility = JavaVersion.toVersion(klogger.versions.javaCompatibility.get())
}

dependencies {
    compileOnly(klogger.android.gradle.plugin)
    compileOnly(klogger.kotlin.gradle.plugin)
    compileOnly(klogger.sqldelight.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("buildModule") {
            id = "build.module"
            implementationClass = "BuildConventionPlugin"
        }
        register("configModule") {
            id = "config.values.module"
            implementationClass = "ConfigValuesConventionPlugin"
        }
        register("kotlinModule") {
            id = "kotlin.module"
            implementationClass = "KotlinModuleConventionPlugin"
        }
        register("commonModule") {
            id = "common.module"
            implementationClass = "CommonModuleConventionPlugin"
        }
        register("commonLibrary") {
            id = "common.library"
            implementationClass = "CommonLibraryConventionPlugin"
        }
        register("jvmTargetModule") {
            id = "jvm.target.library"
            implementationClass = "JvmTargetLibraryConventionPlugin"
        }
        register("androidTargetModule") {
            id = "android.target.library"
            implementationClass = "AndroidTargetLibraryConventionPlugin"
        }
        register("iosTargetModule") {
            id = "ios.target.library"
            implementationClass = "IosTargetLibraryConventionPlugin"
        }
        register("sqldelightModule") {
            id = "sqldelight.module"
            implementationClass = "SQLDelightConventionPlugin"
        }
    }
}
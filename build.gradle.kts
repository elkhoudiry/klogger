plugins {
    alias(klogger.plugins.android.library) apply false
    alias(klogger.plugins.kotlin.jvm) apply false
    alias(klogger.plugins.kotlin.multiplatform) apply false
    alias(klogger.plugins.kotlin.serialization) apply false
    alias(klogger.plugins.ktlint.gradle) apply false
    alias(klogger.plugins.ktor) apply false
    alias(klogger.plugins.sqldelight) apply false
}

group = "io.github.elkhoudiry"
version = "0.0.1"

subprojects {
    apply(plugin = rootProject.klogger.plugins.ktlint.gradle.get().pluginId)

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        filter {
            exclude("*.gradle.kts")
            exclude {
                it.file.path.contains("${buildDir}/generated/")
            }
        }
    }
}

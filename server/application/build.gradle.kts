plugins {
    id(klogger.plugins.kotlin.jvm.get().pluginId)
    id(klogger.plugins.kotlin.serialization.get().pluginId)
    id(klogger.plugins.ktor.get().pluginId)
}

application {
    mainClass.set("io.github.elkhoudiry.klogger.server.application.ApplicationKt")

    val isDevelopment = System.getenv("development")?.toBoolean() ?: false
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":server-data-common"))
    implementation(project(":server-data-database-cassandra"))
    implementation(project(":server-data-events"))
    implementation(project(":server-data-logs"))

    implementation(project(":core-shared"))
    implementation(project(":server-route-events"))
    implementation(project(":server-route-health"))
    implementation(project(":server-route-logs"))

    implementation(klogger.ch.qos.logback)
    implementation(klogger.kotlinx.datetime)
    implementation(klogger.kotlinx.serialization.json)
    implementation(klogger.ktor.server.call.logging.jvm)
    implementation(klogger.ktor.server.call.id.jvm)
    implementation(klogger.ktor.server.compression.jvm)
    implementation(klogger.ktor.server.core.jvm)
    implementation(klogger.ktor.server.default.headers.jvm)
    implementation(klogger.ktor.server.host.common.jvm)
    implementation(klogger.ktor.server.netty.jvm)
    implementation(klogger.ktor.server.cio.jvm)
    implementation(klogger.ktor.server.jetty.jvm)
    implementation(klogger.ktor.server.status.pages.jvm)
    implementation(klogger.ktor.server.content.negotiation.jvm)
    implementation(klogger.ktor.serialization.kotlinx.json.jvm)

    testImplementation(klogger.ktor.server.tests.jvm)
    testImplementation(klogger.kotlin.test)
    testImplementation(klogger.cassandra.embedded)
}

java {
    sourceCompatibility = JavaVersion.toVersion(klogger.versions.javaCompatibility.get())
    targetCompatibility = JavaVersion.toVersion(klogger.versions.javaCompatibility.get())

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(klogger.versions.javaCompatibility.get()))
    }
}
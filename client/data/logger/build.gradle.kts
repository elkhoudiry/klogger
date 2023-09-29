plugins {
    id("common.library")
    id("jvm.target.library")
    id("ios.target.library")
    id("sqldelight.module")
}

commonDependencies {
    implementation(project(":core-shared"))
    implementation(klogger.ktor.client.core)
    implementation(klogger.ktor.client.content.negotiation)
    implementation(klogger.ktor.client.encoding)
    implementation(klogger.ktor.serialization.kotlinx.json)
}
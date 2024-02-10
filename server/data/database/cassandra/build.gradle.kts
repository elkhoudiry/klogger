plugins {
    id("kotlin.module")
    id("server.dependency.module")
}

dependencies {
    implementation(project(":server-data-database-common"))
    implementation(klogger.realtime.cassandra)
    implementation(klogger.cassandra.migration)
}
plugins {
    id("kotlin.module")
    id("server.dependency.module")
}

dependencies {
    api(project(":server-data-database-common"))
    api(klogger.realtime.cassandra)
    api(klogger.cassandra.migration)
}
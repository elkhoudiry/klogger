plugins {
    id("server.route.module")
}

dependencies {
    implementation(project(":server-data-logs"))
    implementation(project(":server-route-common"))
}
plugins {
    id("server.route.module")
}

dependencies {
    implementation(project(":route-common"))
    implementation(project(":server-data-logs"))
}
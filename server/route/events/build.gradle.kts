plugins {
    id("server.route.module")
}

dependencies {
    implementation(project(":server-data-events"))
    implementation(project(":server-route-common"))
}
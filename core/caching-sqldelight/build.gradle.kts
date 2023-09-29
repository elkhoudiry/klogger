plugins {
    id("common.library")
    id("android.target.library")
    id("jvm.target.library")
    id("ios.target.library")
    id(klogger.plugins.sqldelight.get().pluginId)
}

commonDependencies {
    implementation(klogger.sqldelight.runtime)
}

androidDependencies {
    implementation(klogger.sqldelight.android.driver)
}

jvmDependencies {
    implementation(klogger.sqldelight.sqlite.driver)
}

sqldelight {
    databases.create("kommon") {
        generateAsync.set(true)
        packageName.set("kommon.db")
    }
}
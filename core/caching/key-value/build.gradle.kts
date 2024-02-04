plugins {
    id("common.library")
    id("android.target.library")
    id("ios.target.library")
    id("jvm.target.library")
}

kotlin {
    ios {
        compilations.getByName("main") {
            cinterops {
                val observer by creating {
                    defFile(project.file("src/nativeInterop/cinterop/observer.def"))
                    packageName("kmp.observer")
                }
            }
        }
    }
    iosSimulatorArm64 {
        compilations.getByName("main") {
            cinterops {
                val observer by creating {
                    defFile(project.file("src/nativeInterop/cinterop/observer.def"))
                    packageName("kmp.observer")
                }
            }
        }
    }
}
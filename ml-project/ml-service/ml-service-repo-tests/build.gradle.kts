plugins {
    id("build-kmp")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(kotlin("test-common"))
                api(kotlin("test-annotations-common"))

                api(libs.coroutines.core)
                api(libs.coroutines.test)
                implementation(projects.mlServiceCommon)
                implementation(projects.mlServiceRepoCommon)
            }
        }
        commonTest {
            dependencies {
                implementation(projects.mlServiceStubs)
            }
        }
        jvmMain {
            dependencies {
                api(kotlin("test-junit"))
            }
        }
    }
}

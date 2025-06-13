plugins {
    id("build-kmp")
}

kotlin {
    sourceSets {
        val coroutinesVersion: String by project
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(libs.coroutines.core)

                // transport models
                implementation(project(":ml-service-common"))
                implementation(project(":ml-service-api-log1"))

                implementation(project(":ml-service-biz"))
                implementation(project(":ml-service-api-v1-kmp"))
                implementation(projects.mlServiceApiV1Kmp)
                //implementation(project(":ml-service-api-v1-mappers"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(libs.coroutines.test)

                implementation(project(":ml-service-api-v1-kmp"))
                implementation(projects.mlServiceApiV1Kmp)

               // implementation(project(":ml-service-api-v1-mappers"))
               // implementation(projects.mlServiceApiV1Mappers)


            }
        }

        jvmTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        nativeTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

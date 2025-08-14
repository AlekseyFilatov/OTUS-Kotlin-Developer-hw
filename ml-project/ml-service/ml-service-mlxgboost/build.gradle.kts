plugins {
    alias(libs.plugins.dataframe)
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm")
    //id("build-kmp")
}

/*kotlin {
    sourceSets {
        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(libs.cor)
                implementation(project(":ml-service-common"))
                implementation(project(":ml-service-stubs"))
//                implementation(project(":ml-service-mlspark"))
                //api(projects.mlServiceMlxgboost)

                implementation(libs.logback)
                implementation(kotlin("stdlib"))

                //implementation(libs.xgboost.example)
                //testImplementation(kotlin("test-junit5"))
                implementation(libs.coroutines.core)
                implementation(project(":ml-service-common"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                api(libs.coroutines.test)

            }
        }
        jvmMain {
            dependencies {
                implementation(libs.kotlin.dataframe)
                implementation(libs.kotlin.dataframe.jdbc)
                implementation(libs.kotlin.dataframe.core)
                implementation(kotlin("stdlib"))
                implementation(libs.xgboost.core)
                implementation(libs.xgboost.jvm)
            }
        }
        jvmTest {
            dependencies {
               implementation(kotlin("test-junit"))
            }
        }
    }
}*/

dependencies {
    implementation(libs.logback)
    implementation(kotlin("stdlib"))
    implementation(libs.xgboost.core)
    implementation(libs.xgboost.jvm)
    implementation(libs.xgboost.spark)
    implementation(libs.kotlin.dataframe)
    implementation(libs.kotlin.dataframe.jdbc)
    implementation(libs.kotlin.dataframe.core)
    //implementation(libs.xgboost.example)
    testImplementation(kotlin("test-junit"))
    implementation(libs.coroutines.core)
    implementation(projects.mlServiceCommon)
    //implementation(project(":ml-service-common"))
}

tasks {
    test {
        //useJUnitPlatform()
    }
}



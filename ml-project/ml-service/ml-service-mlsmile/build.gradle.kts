plugins {
    alias(libs.plugins.dataframe)
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm")
    //id("build-kmp")
}

dependencies {
    implementation(libs.logback)
    implementation(kotlin("stdlib"))
    implementation(libs.smile.core)
    //testImplementation(kotlin("test-junit"))
    implementation(libs.coroutines.core)
    implementation(projects.mlServiceCommon)
    //implementation(project(":ml-service-common"))

    /*test*/
    testImplementation(kotlin("test-junit5"))
    implementation(libs.coroutines.test)
    implementation(projects.mlServiceMltest)
}

tasks {
    test {
        useJUnitPlatform()
    }
}



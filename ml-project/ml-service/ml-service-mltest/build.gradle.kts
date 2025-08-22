plugins {
    alias(libs.plugins.dataframe)
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm")
    //id("build-kmp")
}

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
    //testImplementation(kotlin("test-junit"))
    implementation(libs.coroutines.core)
    implementation(projects.mlServiceCommon)
    //implementation(project(":ml-service-common"))

    /*test*/
    testImplementation(kotlin("test-junit5"))
    implementation(libs.coroutines.test)
}

tasks {
    test {
        jvmArgs("--add-opens=java.base/sun.nio.ch=ALL-UNNAMED")
        useJUnitPlatform()
    }
}



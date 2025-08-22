plugins {
    id("build-jvm")
}

dependencies {
    implementation(libs.logback)
    implementation(kotlin("stdlib"))
    //implementation(libs.xgboost)
    implementation(libs.xgboost.jvm)
    implementation(libs.xgboost.spark)
    implementation(libs.spark.core)
    implementation(libs.spark.hive)
    implementation(libs.spark.sql)
    implementation(libs.spark.mlib)
    implementation(libs.scala)
    implementation(project(":ml-service-common"))

    /*logs*/
    implementation(libs.log4j.api)
    implementation(libs.log4j.core)
    implementation(libs.log4j.loyout)

    /*test*/
    testImplementation(kotlin("test-junit5"))
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.test)
    implementation(projects.mlServiceMltest)
}


tasks {
    test {
        jvmArgs("--add-opens=java.base/sun.nio.ch=ALL-UNNAMED")
        useJUnitPlatform()
    }
}



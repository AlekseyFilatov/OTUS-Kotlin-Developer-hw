plugins {
    id("build-jvm")
}

dependencies {
    implementation(libs.logback)
    implementation(kotlin("stdlib"))
    implementation(libs.xgboost)
    implementation(libs.xgboost.jvm)
    implementation(libs.xgboost.spark)
    implementation(libs.spark.core)
    implementation(libs.spark.hive)
    compileOnly(libs.spark.sql)
    implementation(libs.spark.mlib)
    implementation(libs.log4j.api)
    implementation(libs.log4j.core)
    implementation(libs.log4j.loyout)
    testImplementation(kotlin("test-junit"))
    implementation(libs.coroutines.core)
    implementation(project(":ml-service-common"))
}


tasks {
    test {
        jvmArgs("--add-opens=java.base/sun.nio.ch=ALL-UNNAMED")
        //useJUnitPlatform()
    }
}



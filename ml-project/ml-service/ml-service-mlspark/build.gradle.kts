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
    implementation(libs.kryo.lib)
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
        jvmArgs("--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
                             "--add-opens=java.base/java.nio=ALL-UNNAMED") /*  --add-opens=java.base/jdk.internal.misc=ALL-UNNAMED " +
        "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED --add-opens=java.management/com.sun.jmx.mbeanserver=ALL-UNNAMED --add-opens=jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED " +
        "--add-opens=java.base/sun.reflect.generics.reflectiveObjects=ALL-UNNAMED " +
        "--add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED --add-opens=java.base/java.io=ALL-UNNAMED " +
        "--add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED " +
        "--add-opens=java.base/java.util.concurrent=ALL-UNNAMED --add-opens=java.base/java.util.concurrent.locks=ALL-UNNAMED --add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.lang.invoke=ALL-UNNAMED --add-opens=java.base/java.math=ALL-UNNAMED --add-opens=java.sql/java.sql=ALL-UNNAMED")*/
        useJUnitPlatform()
    }
}



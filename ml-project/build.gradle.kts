plugins {
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
}

group = "api.kotlinproject"
version = "0.0.1"

allprojects {
  repositories {
    mavenCentral()
  }
}

subprojects {
  repositories {
    mavenCentral()
  }
  group = rootProject.group
  version = rootProject.version
}

tasks {
    register("check") {
        group = "verification"
        dependsOn(gradle.includedBuild("ml-service").task(":check"))
    }
}
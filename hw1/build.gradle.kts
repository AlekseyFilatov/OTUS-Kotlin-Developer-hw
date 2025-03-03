plugins {
  kotlin("jvm") apply false
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
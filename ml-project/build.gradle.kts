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
    register("clean") {
        group = "build"
        gradle.includedBuilds.forEach {
            dependsOn(it.task(":clean"))
        }
    }
    val buildMigrations: Task by creating {
        dependsOn(gradle.includedBuild("ml-service-other").task(":buildImages"))
    }
    val buildImages: Task by creating {
        dependsOn(buildMigrations)
        dependsOn(gradle.includedBuild("ml-service").task(":buildImages"))
    }
/*    val e2eTests: Task by creating {
        dependsOn(buildImages)
        dependsOn(gradle.includedBuild("ml-service-tests").task(":e2eTests"))
        mustRunAfter(buildImages)
    }*/

    create("check") {
        group = "verification"
        dependsOn(buildImages)
        //dependsOn(e2eTests)
    }
}
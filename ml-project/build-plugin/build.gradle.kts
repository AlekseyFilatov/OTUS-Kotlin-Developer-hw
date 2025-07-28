plugins {
    //kotlin("jvm") apply false
    `kotlin-dsl`
}

group = "api.kotlinproject"
version = "0.0.1"

gradlePlugin {
    plugins {
        register("build-jvm") {
            id = "build-jvm"
            implementationClass = "api.kotlinproject.plugin.BuildPluginJvm"
        }
        register("build-kmp") {
            id = "build-kmp"
            implementationClass = "api.kotlinproject.plugin.BuildPluginMultiplatform"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // enable Ktlint formatting
//    add("detektPlugins", libs.plugin.detektFormatting)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.plugin.kotlin)
//    implementation(libs.plugin.dokka)
    implementation(libs.plugin.binaryCompatibilityValidator)
//    implementation(libs.plugin.mavenPublish)
    implementation(libs.testcontainers.core)
}

/*subprojects {
    repositories {
        mavenCentral()
    }
    group = rootProject.group
    version = rootProject.version
}

ext {
    val specDir = layout.projectDirectory.dir("../specs")
    set("spec-v1", specDir.file("specs-ml-v1.yaml").toString())
    //set("spec-v2", specDir.file("specs-ml-v2.yaml").toString())
}

tasks {
    arrayOf("build", "clean", "check").forEach { tsk ->
        register(tsk) {
            group = "build"
            dependsOn(subprojects.map { it.getTasksByName(tsk, false) })
        }
    }
}*/
pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

plugins{
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

/*генерация строки зависимости при сборке*/
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "kotlinproject"
include("m1l1-first")
/*композитные подпроекты*/
//includeBuild("plugin")
includeBuild("lessons")
includeBuild("project")

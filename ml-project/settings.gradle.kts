pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

plugins{
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

/*генерация строки зависимости при сборке*/
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "kotlinproject"
//include("m1l1-first")
/*композитные подпроекты*/
includeBuild("build-plugin")
//includeBuild("lessons")
includeBuild("ml-service")
includeBuild("ml-service-libs")
includeBuild("ml-service-other")
//includeBuild("ml-service-tests")


/*plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "kotlinproject"
include("app")*/
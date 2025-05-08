plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))

    implementation(projects.mlServiceCommon)
    implementation(projects.mlServiceApiV1Jackson)
    implementation(projects.mlServiceApiV1Kmp)
    implementation(projects.mlServiceStubs)
    testImplementation(kotlin("test-junit"))

}

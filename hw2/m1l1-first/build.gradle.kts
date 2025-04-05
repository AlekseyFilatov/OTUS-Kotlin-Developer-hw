plugins {
  kotlin("jvm")
}

val coroutinesVersion: String by project

dependencies {
  //testImplementation("org.jetbrains.kotlin:kotlin-test")
  testImplementation(kotlin("test"))

  implementation(kotlin("stdlib"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

  // Homework Hard
  implementation("com.squareup.okhttp3:okhttp:4.12.0") // http client
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2") // from string to object

  //testImplementation(kotlin("test-junit"))
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(21)
}

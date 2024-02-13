plugins {
    // Apply the groovy plugin to also add support for Groovy (needed for Spock)
    groovy

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

group = "io.github.t45k"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("jakarta.annotation:jakarta.annotation-api:3.0.0-M1")

    // Use the latest Groovy version for Spock testing
    testImplementation(libs.groovy)

    // Use the awesome Spock testing and specification framework even with Java
    testImplementation(libs.spock.core)
    testImplementation(libs.junit)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

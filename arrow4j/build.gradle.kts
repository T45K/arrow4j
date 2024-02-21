plugins {
    java

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

    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
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

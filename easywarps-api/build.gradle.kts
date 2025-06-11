plugins {
    id("java-library")
}

dependencies {
    compileOnly(rootProject.ext["paperApi"].toString())
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()
    withJavadocJar()
}
plugins {
    id("java-library")
    id("com.gradleup.shadow")
    id("io.freefair.lombok") version "8.13.1"
}

dependencies {
    compileOnly(rootProject.ext["paperApi"].toString())
    implementation(project(":easywarps-api"))
    implementation(rootProject.ext["bstats"].toString())
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.shadowJar {
    archiveBaseName.set("EasyWarps")
    archiveClassifier.set("")
    relocate("org.bstats", "com.pritam.easywarps.libs.bstats")
    minimize()
}

tasks.processResources {
    val props = mapOf("version" to project.version)
    inputs.properties(props)
    filteringCharset = "UTF-8"

    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}


plugins {
    id("java-library")
    id("com.gradleup.shadow") version "9.0.0-beta13"
}

val paperVersion = "1.21.5-R0.1-SNAPSHOT"
val bstatsVersion = "3.0.2"

repositories {
    mavenCentral()
}

allprojects {
    group = "com.pritam.easywarps"
    version = "1.0.0-beta1"

    ext {
        set("paperVersion", paperVersion)
        set("paperApi", "io.papermc.paper:paper-api:$paperVersion")
        set("bstats", "org.bstats:bstats-bukkit:$bstatsVersion")
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
}

subprojects {
    apply(plugin = "java-library")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<ProcessResources> {
        filteringCharset = "UTF-8"
    }
}

tasks.register("buildAll") {
    dependsOn(":easywarps-plugin:shadowJar")
    group = "build"
    description = "Builds all modules and creates a combined jar"
}

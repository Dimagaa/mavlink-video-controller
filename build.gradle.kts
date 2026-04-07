plugins {
    kotlin("jvm") version "2.2.0"
    application
    id("com.gradleup.shadow") version "9.4.1"
}

application {
    mainClass.set("com.app.reactivestreams.MainKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.app.reactivestreams.MainKt"
    }
}

tasks.shadowJar {
    archiveClassifier.set("all")
    configurations = listOf(project.configurations.runtimeClasspath.get())
    manifest {
        attributes["Main-Class"] = "com.app.reactivestreams.MainKt"
    }
    mergeServiceFiles()
}

group = "org.piu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.21.2")
    implementation("org.freedesktop.gstreamer:gst1-java-core:1.4.0")
}

tasks.test {
    useJUnitPlatform()
}


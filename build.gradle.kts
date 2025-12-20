plugins {
    kotlin("jvm") version "2.2.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

group = "com.nami"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:6.2.0")
}

application {
    mainClass.set("com.nami.MainKt")
}
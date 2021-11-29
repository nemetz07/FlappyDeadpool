import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    application
    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "hu.gcbno3"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}

dependencies {
    implementation("com.github.almasb:fxgl:11.17")
    implementation("org.json:json:20210307")
}

javafx {
    version = "16"
    modules = listOf("javafx.controls", "javafx.fxml")
}
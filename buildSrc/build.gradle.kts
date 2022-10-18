@file:Suppress("UnstableApiUsage")

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {

    implementation(libs.gradle.android)
    implementation(libs.gradle.kotlin)
    implementation(libs.dokka.core)
}

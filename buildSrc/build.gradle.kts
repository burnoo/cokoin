@file:Suppress("UnstableApiUsage")

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {

    implementation(libs.findDependency("gradle.android").get())
    implementation(libs.findDependency("gradle.kotlin").get())
}
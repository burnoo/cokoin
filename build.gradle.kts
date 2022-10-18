@file:Suppress("UnstableApiUsage")
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.dependencyUpdates)
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.gradle.kotlin)
        classpath(libs.gradle.android)
        classpath(libs.gradle.jetbrainsCompose)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        // Needed for dokka
        maven(url = "https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    }

    if (!displayName.contains("example")) {
        plugins.apply("org.jetbrains.dokka")
    }
}

fun getStabilityLevel(version: String) : Int {
    val v = version.toUpperCase()
    return when {
        v.contains("ALPHA") -> 0
        v.contains("BETA") -> 1
        v.contains(Regex("(?:RC)|(?:-M[0-9]+)")) -> 2
        else -> 3
    }
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        getStabilityLevel(candidate.version) < getStabilityLevel(currentVersion)
    }
}

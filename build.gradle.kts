plugins {
    id("org.jetbrains.dokka") version Versions.dokka
    id("com.github.ben-manes.versions") version Versions.gradleVersions

}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    dependencies {
        // __LATEST_COMPOSE_RELEASE_VERSION__
        classpath(Deps.JetbrainsCompose.gradlePlugin)
        classpath(Deps.androidGradlePlugin)
        classpath(kotlin("gradle-plugin", version = Versions.kotlin))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        // Needed for dokka
        maven(url = "https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    }

    if (!displayName.contains("example")) {
        plugins.apply("org.jetbrains.dokka")
    }
}
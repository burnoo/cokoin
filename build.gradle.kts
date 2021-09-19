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

tasks.register("publishCokoinToSonatype") {
    dependsOn(
        ":cokoin:publishAllPublicationsToSonatypeRepository",
        ":cokoin-jetpack:publishAllPublicationsToSonatypeRepository"
    )
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
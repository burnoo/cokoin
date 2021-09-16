buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    dependencies {
        // __LATEST_COMPOSE_RELEASE_VERSION__
        classpath("org.jetbrains.compose:compose-gradle-plugin:1.0.0-alpha3")
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath(kotlin("gradle-plugin", version = "1.5.21"))
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
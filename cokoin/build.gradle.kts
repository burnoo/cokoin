import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("sonatype-publish")
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }
    sourceSets {
        named("commonMain") {
            dependencies {
                api(compose.runtime)
                api(Deps.Koin.core)
            }
        }
        named("jvmMain")
        named("jsMain")
    }

    sourceSets.all {
        languageSettings.optIn("kotlin.RequiresOptIn")
    }
}
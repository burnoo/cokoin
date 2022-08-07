@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

import org.jetbrains.compose.ExperimentalComposeLibrary
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
                api(libs.koin.core)
            }
        }
        named("jvmTest") {
            dependencies {
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.uiTestJUnit4)
                implementation(compose.desktop.currentOs)
                implementation(compose.material)
                implementation(libs.testCore)
                implementation(libs.koin.junit4)
            }
        }
        named("jvmMain")
        named("jsMain")
    }

    sourceSets.all {
        languageSettings.optIn("kotlin.RequiresOptIn")
    }
}
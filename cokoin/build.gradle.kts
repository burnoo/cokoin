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
                implementation(getSkiaDependency())
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

fun getSkiaDependency() : String {
    val osName = System.getProperty("os.name")
    val targetOs = when {
        osName == "Mac OS X" -> "macos"
        osName.startsWith("Win") -> "windows"
        osName.startsWith("Linux") -> "linux"
        else -> error("Unsupported OS: $osName")
    }

    val targetArch = when (val osArch = System.getProperty("os.arch")) {
        "x86_64", "amd64" -> "x64"
        "aarch64" -> "arm64"
        else -> error("Unsupported arch: $osArch")
    }

    val version = "0.6.8"
    val target = "${targetOs}-${targetArch}"
    return "org.jetbrains.skiko:skiko-jvm-runtime-$target:$version"
}
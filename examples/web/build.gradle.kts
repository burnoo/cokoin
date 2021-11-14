import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

rootProject.extensions.configure<NodeJsRootExtension> {
    versions.webpackCli.version = "4.9.1"
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation(project(":cokoin"))
            }
        }
    }
}
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
                api("io.insert-koin:koin-core:3.1.2")
            }
        }
    }
}
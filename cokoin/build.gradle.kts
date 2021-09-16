import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("sonatype-publish")
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }
    android {
        publishLibraryVariants("release")
    }
    sourceSets {
        named("commonMain") {
            dependencies {
                api(compose.runtime)
                api("io.insert-koin:koin-core:3.1.2")
            }
        }
        named("androidMain") {
            dependencies {
                api(compose.ui)
                api("io.insert-koin:koin-android:3.1.2")
                api("androidx.navigation:navigation-compose:2.4.0-alpha09")
                api("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0-beta01")
            }
        }
    }

    sourceSets.all {
        languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
    }
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
    }
}
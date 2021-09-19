plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.compose")
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
}

kotlin.sourceSets.all {
    languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
}

dependencies {
    api(compose.runtime)
    api("io.insert-koin:koin-android:3.1.2")
    api("androidx.navigation:navigation-compose:2.4.0-alpha09")
    implementation(project(":cokoin"))
}

import org.jetbrains.compose.compose

plugins {
    id("org.jetbrains.compose")
    id("common-android-library")
}

dependencies {
    api(project(":cokoin"))
    api(compose.runtime)
    api(libs.koin.android)
    api(libs.jetpackCompose.navigation)

    implementation(libs.jetpackCompose.ui)
    implementation(project(":cokoin-android-viewmodel"))
}
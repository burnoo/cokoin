plugins {
    id("org.jetbrains.compose")
    id("common-android-library")
}

dependencies {
    api(project(":cokoin"))
    api(compose.runtime)
    api(libs.koin.android)
    api(libs.jetpackCompose.navigation)

    implementation(project(":cokoin-android-viewmodel"))
}
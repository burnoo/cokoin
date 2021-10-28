plugins {
    id("org.jetbrains.compose")
    id("common-android-library")
}

dependencies {
    api(project(":cokoin"))
    api(compose.runtime)
    api(libs.koin.android)
    implementation(libs.jetpackCompose.viewModel)

    androidTestImplementation(libs.jetpackCompose.navigation)
}
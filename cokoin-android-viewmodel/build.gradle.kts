import org.jetbrains.compose.compose

plugins {
    id("org.jetbrains.compose")
    id("common-android-library")
}

dependencies {
    api(project(":cokoin"))
    api(compose.runtime)
    api(libs.koin.android)

    implementation(libs.jetpackCompose.activity)
    implementation(libs.jetpackCompose.ui)
    implementation(libs.jetpackCompose.viewModel)

    androidTestImplementation(libs.jetpackCompose.navigation)
}
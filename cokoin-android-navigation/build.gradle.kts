plugins {
    id("org.jetbrains.compose")
    id("common-android-library")
}

dependencies {
    api(project(":cokoin"))
    api(compose.runtime)
    api(Deps.Koin.android)
    api(Deps.JetpackCompose.navigation)
}
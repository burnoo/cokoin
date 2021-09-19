plugins {
    id("common-android-library")
    id("common-jetpack")
}

dependencies {
    api(project(":cokoin-jetpack"))
    api(Deps.JetpackCompose.runtime)
    api(Deps.Koin.android)
    implementation(Deps.JetpackCompose.viewModel)
}

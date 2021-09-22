plugins {
    id("common-android-library")
    id("common-jetpack")
}

dependencies {
    api(Deps.JetpackCompose.runtime)
    api(Deps.Koin.core)

    androidTestImplementation(Deps.Koin.junit4)
}
plugins {
    id("com.android.library")
}

android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.jetpackCompose
        kotlinCompilerVersion = Versions.kotlin
    }
}

dependencies {
    debugImplementation(Deps.JetpackCompose.uiTestManifest)
    androidTestImplementation(Deps.JetpackCompose.uiTestJUnit)
    androidTestImplementation(Deps.testCore)
    androidTestImplementation(Deps.JetpackCompose.material)
}

configurations.all {
    resolutionStrategy {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-debug")
    }
}
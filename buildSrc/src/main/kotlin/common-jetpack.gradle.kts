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

val pathMap = mapOf(
    "cokoin-jetpack" to "../cokoin/src/commonMain/kotlin",
    "cokoin-jetpack-viewmodel" to "../cokoin-android-viewmodel/src/main/java",
    "cokoin-jetpack-navigation" to "../cokoin-android-navigation/src/main/java",
)
tasks.register<Copy>("copyCokoinForJetpack") {
    from(layout.projectDirectory.dir(pathMap.getValue(project.name)))
    into(layout.projectDirectory.dir("src/main/java"))
}
tasks.named("preBuild") { dependsOn("copyCokoinForJetpack") }
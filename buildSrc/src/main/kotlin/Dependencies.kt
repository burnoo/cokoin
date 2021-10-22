object Versions {
    const val kotlin = "1.5.31"
    const val androidGradlePlugin = "4.2.2"
    const val koin = "3.1.2"
    const val jetbrainsCompose = "0.0.0-master-build423"
    const val jetpackCompose = "1.0.4"
    const val jetpackComposeViewModel = "2.4.0-rc01"
    const val jetpackComposeNavigation = "2.4.0-alpha10"
    const val testCore = "1.4.0"
    const val dokka = "1.5.31"
    const val gradleVersions = "0.39.0"
}

object Deps {

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
        const val junit4 = "io.insert-koin:koin-test-junit4:${Versions.koin}"
    }

    object JetpackCompose {
        const val runtime = "androidx.compose.runtime:runtime:${Versions.jetpackCompose}"
        const val material = "androidx.compose.material:material:${Versions.jetpackCompose}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.jetpackComposeViewModel}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.jetpackComposeNavigation}"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.jetpackCompose}"
        const val uiTestJUnit = "androidx.compose.ui:ui-test-junit4:${Versions.jetpackCompose}"
    }

    object JetbrainsCompose {
        const val gradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:${Versions.jetbrainsCompose}"
    }

    const val testCore = "androidx.test:core:${Versions.testCore}"
}

object Versions {
    const val kotlin = "1.5.21"
    const val androidGradlePlugin = "4.2.2"
    const val koin = "3.1.2"
    const val jetbrainsCompose = "1.0.0-alpha3"
    const val jetpackCompose = "1.0.2"
    const val jetpackComposeViewModel = "2.4.0-beta01"
    const val jetpackComposeNavigation = "2.4.0-alpha09"
    const val testCore = "1.4.0"
}

object Deps {

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
    }

    object JetpackCompose {
        const val runtime = "androidx.compose.runtime:runtime:${Versions.jetpackCompose}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.jetpackComposeViewModel}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.jetpackComposeNavigation}"
    }

    object JetbrainsCompose {
        const val gradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:${Versions.jetbrainsCompose}"
    }
}
# 🥥 Cokoin

[![Maven Central](https://img.shields.io/maven-central/v/dev.burnoo/cokoin)](https://search.maven.org/search?q=dev.burnoo.cokoin)

Injection library for Compose (Multiplatform and Jetpack), Koin wrapper. It uses `@Composable`
functions to configure `KoinContext`.

## Installation

Library is hosted on Maven Central. Add following the packages to your module `build.gradle.kts` or `build.gradle`:

```kotlin
dependencies {
    // for JetBrains' Compose Multiplatform
    implementation("dev.burnoo:cokoin:0.2.0")
    implementation("dev.burnoo:cokoin-android-viewmodel:0.2.0") // for Androidx ViewModel
    implementation("dev.burnoo:cokoin-android-navigation:0.2.0") // for Compose Navigation

    // for Google's Jetpack Compose
    implementation("dev.burnoo:cokoin-jetpack:0.2.0")
    implementation("dev.burnoo:cokoin-jetpack-viewmodel:0.2.0") // for Androidx ViewModel
    implementation("dev.burnoo:cokoin-jetpack-navigation:0.2.0") // for Compose Navigation

    // REMOVE "org.koin:koin-androidx-compose:X.Y.Z" if you were using it
}
```

## Usage

The library is using Koin's Application and Modules. The Koin documentation can be found here: https://insert-koin.io/.

The core library contains [`@Composable Koin`](cokoin/src/commonMain/kotlin/dev/burnoo/cokoin/Koin.kt) function, which is used to configure Koin application. It can be used with `@Preview`.

```kotlin
val appModule = module {
    factory { "Hello, DI!" }
}

@Preview
@Composable
fun App() {
    Koin(appDeclaration = { modules(appModule) }) {
        val text = get<String>()
        Text(text)
    }
}
```

### Scopes

Scopes are created by wrapping composables in [`@Composable KoinScope`](cokoin/src/commonMain/kotlin/dev/burnoo/cokoin/Scope.kt):

```kotlin
data class A(val value: String)

val scopedModule = module {
    scope<ScopeTypeA> {
        scoped { A("scopeA") }
    }
    scope<ScopeTypeB> {
        scoped { A("scopeB") }
    }
}

@Preview
@Composable
fun Test() {
    Koin(appDeclaration = { modules(scopedModule) }) {
        Column {
            KoinScope(getScope = { createScope<ScopeTypeA>() }) {
                Text(get<A>().value)
            }
            KoinScope(getScope = { createScope<ScopeTypeB>() }) {
                Text(get<A>().value)
            }
        }
    }
}
```

### ViewModel

Dependencies:

- `dev.burnoo:cokoin-android-viewmodel:x.y.z` (Multiplatform)
- `dev.burnoo:cokoin-jetpack-viewmodel:x.y.z` (Jetpack)

Call `getViewModel` to obtain `ViewModel` inside `@Composable`:

```kotlin
class MainViewModel : ViewModel() {
    val data = "Hello, DI!"
}

private val viewModelModule = module {
    viewModel { MainViewModel() }
}

@Composable
fun ViewModelSample() {
    Koin(appDeclaration = { modules(viewModelModule) }) {
        val viewModel = getViewModel<MainViewModel>()
        Text(viewModel.data)
    }
}
```

`getViewModel`
supports [Koin's injection parameters](https://insert-koin.io/docs/reference/koin-android/viewmodel/#viewmodel-and-injection-parameters)
and `SaveStateHandle`. Advanced examples can be
found [here](cokoin-jetpack-viewmodel/src/androidTest/java/dev/burnoo/cokoin/viewmodel/ViewModelTest.kt).

### Compose Navigation

Dependencies:

- `dev.burnoo:cokoin-android-navigation:x.y.z` (Multiplatform)
- `dev.burnoo:cokoin-jetpack-navigation:x.y.z` (Jetpack)

First replace `NavHost` with `KoinNavHost`:

```kotlin
@Composable
fun Sample() {
    val navController = rememberNavController()
    Koin(appDeclaration = { modules(viewModelModule) }) {
        KoinNavHost(navController, startDestination = "1") {
            composable("1") {
                Screen1()
            }
        }
    }
}
```

Then use `getNavController` and `getNavViewModel` inside `composable` screen.

```kotlin
@Composable
fun Screen1() {
    val navController = getNavController()
    Button(onClick = { navController.navigate("2") }) {
        val navViewModel = getNavViewModel<MainViewModel>()
        //...
    }
}
```

## License

```
Copyright 2021 Bruno Wieczorek

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
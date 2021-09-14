# 🥥 Cokoin
[![Maven Central](https://img.shields.io/maven-central/v/dev.burnoo/cokoin)](https://search.maven.org/search?q=dev.burnoo.cokoin)

Dependency Injection library for Compose Multiplatform, Koin wrapper.
It uses `@Composable` functions to configure `KoinContext` and `Scopes`.

## Installation
Library is hosted on Maven Central. Add following the package to your module `build.gradle.kts`:
```kotlin
dependencies {
    implementation("dev.burnoo:cokoin:0.1.2")
}
```

## Usage
The library is using Koin's Application and Modules. The Koin documentation can be found here: https://insert-koin.io/.

The library itself contains `@Composable` functions, which helps with Koin configuration:
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
Scopes are also supported:
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
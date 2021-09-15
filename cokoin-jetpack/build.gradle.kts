plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.2"
        kotlinCompilerVersion = "1.5.21"
    }
}

tasks.register<Copy>("copyCokoinForJetpack") {
    from(layout.projectDirectory.dir("../cokoin/src/commonMain/kotlin"))
    into(layout.projectDirectory.dir("src/main/java"))
}

tasks.named("preBuild") { dependsOn("copyCokoinForJetpack") }

dependencies {
    api("io.insert-koin:koin-core:3.1.2")
    api("androidx.compose.runtime:runtime:1.0.2")
}

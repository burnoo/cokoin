plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
    id("sonatype-publish")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.jetpackCompose
        kotlinCompilerVersion = Versions.kotlin
    }
    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/*.kotlin_module")
    }
}

kotlin.sourceSets.all {
    languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
}

dependencies {
    api(Deps.Koin.core)
    api(Deps.Koin.android)
    api(Deps.JetpackCompose.runtime)
    api(Deps.JetpackCompose.viewModel)
    api(Deps.JetpackCompose.navigation)

    debugImplementation("androidx.compose.ui:ui-test-manifest:${Versions.jetpackCompose}")
    androidTestImplementation("androidx.compose.material:material:${Versions.jetpackCompose}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.jetpackCompose}")
    androidTestImplementation("androidx.test:core:${Versions.testCore}")
}

tasks.register<Copy>("copyCokoinForJetpack") {
    from(
        layout.projectDirectory.dir("../cokoin/src/commonMain/kotlin"),
        layout.projectDirectory.dir("../cokoin/src/androidMain/kotlin")
    )
    into(layout.projectDirectory.dir("src/main/java"))
}

tasks.named("preBuild") { dependsOn("copyCokoinForJetpack") }

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.named("main").get().java.srcDirs)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components.named("release").get())
                artifact(sourcesJar.get())
                artifactId = project.name
            }
        }
    }
}
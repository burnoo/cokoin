plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.compose")
    id("maven-publish")
    id("sonatype-publish")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kotlin.sourceSets.all {
    languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
}

dependencies {
    api(project(":cokoin"))
    api(compose.runtime)
    api(Deps.Koin.android)
    implementation(Deps.JetpackCompose.viewModel)
}

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
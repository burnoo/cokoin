@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    kotlin("android")
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
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.RequiresOptIn")
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

configurations.all {
    resolutionStrategy {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-debug")
    }
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {

    debugImplementation(libs.findDependency("jetpackCompose.uiTestManifest").get())
    androidTestImplementation(libs.findDependency("jetpackCompose.uiTestJUnit").get())
    androidTestImplementation(libs.findDependency("jetpackCompose.material").get())
    androidTestImplementation(libs.findDependency("testCore").get())
}
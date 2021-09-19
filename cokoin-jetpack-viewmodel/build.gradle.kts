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
    api(project(":cokoin-jetpack"))
    api(Deps.JetpackCompose.runtime)
    api(Deps.Koin.android)
    implementation(Deps.JetpackCompose.viewModel)

    debugImplementation(Deps.JetpackCompose.uiTestManifest)
    androidTestImplementation(Deps.JetpackCompose.uiTestJUnit)
    androidTestImplementation(Deps.testCore)
    androidTestImplementation(Deps.JetpackCompose.material)
}

tasks.register<Copy>("copyCokoinForJetpack") {
    from(layout.projectDirectory.dir("../cokoin-android-viewmodel/src/main"))
    into(layout.projectDirectory.dir("src/main"))
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
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
        kotlinCompilerExtensionVersion = "1.0.2"
        kotlinCompilerVersion = "1.5.21"
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
    val composeVersion = "1.0.2"

    api("io.insert-koin:koin-core:3.1.2")
    api("io.insert-koin:koin-android:3.1.2")
    api("androidx.compose.runtime:runtime:$composeVersion")
    api("androidx.navigation:navigation-compose:2.4.0-alpha09")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0-beta01")

    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    androidTestImplementation("androidx.compose.material:material:$composeVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    androidTestImplementation("androidx.test:core:1.4.0")
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
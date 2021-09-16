plugins {
    id("com.android.library")
    id("maven-publish")
    id("sonatype-publish")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 21
        targetSdk = 30
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

dependencies {
    api("io.insert-koin:koin-core:3.1.2")
    api("androidx.compose.runtime:runtime:1.0.2")
}

tasks.register<Copy>("copyCokoinForJetpack") {
    from(layout.projectDirectory.dir("../cokoin/src/commonMain/kotlin"))
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
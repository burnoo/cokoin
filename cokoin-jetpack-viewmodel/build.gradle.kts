plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
    id("sonatype-publish")
    id("common-android-library")
    id("common-jetpack")
}

dependencies {
    api(project(":cokoin-jetpack"))
    api(Deps.JetpackCompose.runtime)
    api(Deps.Koin.android)
    implementation(Deps.JetpackCompose.viewModel)
}

tasks.register<Copy>("copyCokoinForJetpack") {
    from(layout.projectDirectory.dir("../cokoin-android-viewmodel/src/main"))
    into(layout.projectDirectory.dir("src/main"))
}

tasks.named("preBuild") { dependsOn("copyCokoinForJetpack") }

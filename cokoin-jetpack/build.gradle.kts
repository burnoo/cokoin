plugins {
    id("com.android.library")
    kotlin("android")
    id("common-android-library")
    id("common-jetpack")
}

dependencies {
    api(Deps.Koin.core)
    api(Deps.JetpackCompose.runtime)
}

tasks.register<Copy>("copyCokoinForJetpack") {
    from(layout.projectDirectory.dir("../cokoin/src/commonMain/kotlin"))
    into(layout.projectDirectory.dir("src/main/java"))
}

tasks.named("preBuild") { dependsOn("copyCokoinForJetpack") }
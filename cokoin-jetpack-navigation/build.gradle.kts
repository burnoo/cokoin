plugins {
    id("common-android-library")
    id("common-jetpack")
}

dependencies {
    api(project(":cokoin-jetpack"))
    api(Deps.JetpackCompose.runtime)
    api(Deps.Koin.android)
    api(Deps.JetpackCompose.navigation)
}

tasks.register<Copy>("copyCokoinForJetpack") {
    from(layout.projectDirectory.dir("../cokoin-android-navigation/src/main"))
    into(layout.projectDirectory.dir("src/main"))
}

tasks.named("preBuild") { dependsOn("copyCokoinForJetpack") }
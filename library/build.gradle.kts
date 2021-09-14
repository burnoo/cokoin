import org.jetbrains.compose.compose
import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("maven-publish")
    id("signing")
}

group = "dev.burnoo"
version = "0.1.0"

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
    jvm()
    js(IR) {
        browser()
    }
    sourceSets {
        named("commonMain") {
            dependencies {
                api(compose.runtime)
                api("io.insert-koin:koin-core:3.1.2")
            }
        }
    }
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

    sourceSets {
        named("main") {
            manifest.srcFile("AndroidManifest.xml")
        }
    }
}

ext {
    val localPropertiesFile = project.rootProject.file("local.properties")
    val properties = Properties()
    val isLocal = localPropertiesFile.exists()
    if (isLocal) {
        properties.load(FileInputStream(localPropertiesFile))
    }
    set("signing.keyId", if(isLocal) properties.getProperty("signing.keyId") else System.getenv("SIGNING_KEY_ID"))
    set("signing.password", if (isLocal) properties.getProperty("signing.password") else System.getenv("SIGNING_PASSWORD"))
    set("signing.secretKeyRingFile", if (isLocal) properties.getProperty("signing.secretKeyRingFile") else System.getenv("SIGNING_SECRET_KEY_RING_FILE"))
    set("ossrhUsername", if (isLocal) properties.getProperty("ossrhUsername") else System.getenv("OSSRH_USERNAME"))
    set("ossrhPassword", if (isLocal) properties.getProperty("ossrhPassword") else System.getenv("OSSRH_PASSWORD"))
    set("sonatypeStagingProfileId", if (isLocal) properties.getProperty("sonatypeStagingProfileId") else System.getenv("SONATYPE_STAGING_PROFILE_ID"))
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    signing {
        sign(publishing.publications)
    }
    repositories {
        maven {
            name = "sonatype"
            val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            url = if (version.toString().endsWith("SNAPSHOT")) {
                uri(snapshotsRepoUrl)
            } else {
                uri(releasesRepoUrl)
            }
            credentials {
                username = project.ext.get("ossrhUsername").toString()
                password = project.ext.get("ossrhPassword").toString()
            }
        }
    }
    publications.withType<MavenPublication> {
        artifact(javadocJar.get())
        pom {
            name.set("cokoin")
            description.set("")
            url.set("https://github.com/burnoo/cokoin")

            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("burnoo")
                    name.set("Bruno Wieczorek")
                    email.set("btuno.wieczorek@gmail.com")
                }
            }
            scm {
                url.set("https://github.com/burnoo/cokoin")
                connection.set("scm:git:git://github.com/burnoo/cokoin.git")
                developerConnection.set("scm:git:git://github.com/burnoo/cokoin.git")
            }
        }
    }
}
import java.io.FileInputStream
import java.util.*

plugins {
    id("maven-publish")
    id("signing")
}

group = "dev.burnoo"
version = "0.3.1"

ext {
    val localPropertiesFile = project.rootProject.file("local.properties")
    val properties = Properties()
    val isLocal = localPropertiesFile.exists()
    if (isLocal) {
        properties.load(FileInputStream(localPropertiesFile))
    }
    set("signing.keyId", if (isLocal) properties.getProperty("signing.keyId") else System.getenv("SIGNING_KEY_ID"))
    set(
        "signing.password",
        if (isLocal) properties.getProperty("signing.password") else System.getenv("SIGNING_PASSWORD")
    )
    set(
        "signing.secretKeyRingFile",
        if (isLocal) properties.getProperty("signing.secretKeyRingFile") else System.getenv("SIGNING_SECRET_KEY_RING_FILE")
    )
    set("ossrhUsername", if (isLocal) properties.getProperty("ossrhUsername") else System.getenv("OSSRH_USERNAME"))
    set("ossrhPassword", if (isLocal) properties.getProperty("ossrhPassword") else System.getenv("OSSRH_PASSWORD"))
    set(
        "sonatypeStagingProfileId",
        if (isLocal) properties.getProperty("sonatypeStagingProfileId") else System.getenv("SONATYPE_STAGING_PROFILE_ID")
    )
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    dependsOn("dokkaHtml")
    from("$buildDir/dokka/html")
}

afterEvaluate {
    publishing {
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
                name.set(project.name)
                description.set("Dependency Injection library for Compose Multiplatform, Koin wrapper.")
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
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
    }
    sign(publishing.publications)
}

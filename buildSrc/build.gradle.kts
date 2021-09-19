plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.2.2")
    implementation(kotlin("gradle-plugin", version = "1.5.21"))
}
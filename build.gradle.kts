group = "com.github.mejiomah17.compose.chart"
version = "0.1.0-SNAPSHOT"

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

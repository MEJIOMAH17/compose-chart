plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group "com.github.mejiomah17"
version "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(project(":pie"))
    implementation("androidx.activity:activity-compose:1.3.0")
    // implementation("io.coil-kt:coil-compose:2.2.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.core:core-ktx:1.3.1")
}

android {
    compileSdkVersion(31)
    defaultConfig {
        applicationId = "com.github.mejiomah17.android"
        minSdkVersion(24)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

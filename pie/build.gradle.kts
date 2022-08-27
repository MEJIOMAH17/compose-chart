import org.jetbrains.compose.compose

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

dependencies {
    api(compose.runtime)
    api(compose.foundation)
    api(compose.material)
}
kotlin {
    explicitApi = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Strict
}

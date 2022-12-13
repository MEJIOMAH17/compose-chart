import org.jetbrains.compose.ComposeBuildConfig
import org.jetbrains.compose.compose

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    `maven-publish`
}

dependencies {
    api(compose.runtime)
    api(compose.foundation)
    api(compose.material)
    implementation("org.jetbrains.compose.ui:ui-text:${ComposeBuildConfig.composeVersion}")
    implementation(compose.preview)
}
kotlin {
    explicitApi = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Strict
}
publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/MEJIOMAH17/compose-chart")
            credentials {
                val githubToken: String by project
                val githubUser: String by project
                username = githubUser
                password = githubToken
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            this.groupId = rootProject.group.toString()
            this.artifactId = project.name
            this.version = rootProject.version.toString()
        }
    }
}

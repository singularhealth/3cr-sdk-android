plugins {
    id("maven-publish")
}
buildscript {
    repositories {
        jcenter()
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
        classpath(libs.gradle)
    }

}
dependencies {
    project(":viewer-sdk-android")
}
subprojects {
    apply {
        plugin("maven-publish")
    }
    publishing {
        publications {
            create<MavenPublication>(project.name) {
                artifactId = "sdk-3cr-android"
                group = "com.github.elliottcooper"
                version = "1.0.22"
                artifact("build/outputs/aar/${project.name}-debug.aar"){
                }
            }
        }
    }
}

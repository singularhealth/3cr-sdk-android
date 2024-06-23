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
                version = "1.0.24"
                artifact("build/outputs/aar/${project.name}-debug.aar")
                pom {
                    name = "@3cr/sdk-android"
                    description = "SDK to build and deploy 3Dicom Core Renderer for Android"
                    url = "https://docs.3cr.singular.health"
                    licenses {
                        license {
                            name = "The Apache License, Version 2.0"
                            url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                        }
                    }
                    developers {
                        developer {
                            id = "elliottcooper"
                            name = "Elliott Cooper"
                            email = "ecooper@singular.health"
                        }
                    }
                    scm {
                        connection = "scm:git:git://github.com/elliottcooper/sdk-3cr-android"
                        developerConnection = "scm:git:ssh://github.com/elliottcooper/sdk-3cr-android.git"
                        url = "https://github.com/elliottcooper/sdk-3cr-android"
                    }
                }
            }
        }
    }
}

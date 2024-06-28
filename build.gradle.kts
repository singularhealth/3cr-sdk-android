import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.internal.impldep.com.fasterxml.jackson.core.JsonPointer.compile
import java.net.URI
import java.util.Properties

plugins {
    id("maven-publish")
    id("ca.stellardrift.publish-github-release") version "0.1.0"

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
//afterEvaluate {
    group = "com.github.singularhealth"
    version = "1.2.9"
//    apply {
//        plugin("maven-publish")
//        plugin("ca.stellardrift.publish-github-release")
//    }
    publishing {
        publications {
            create<MavenPublication>(project.name) {
                artifactId = "3cr-sdk-android"
                artifact("viewer-sdk-android/build/outputs/aar/viewer-sdk-android-debug.aar")
                fileTree("*.jar").forEach {
                    compile(it.path)
                }
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
                            id = "singularhealth"
                            name = "Elliott Cooper"
                            email = "ecooper@singular.health"
                        }
                    }
                    scm {
                        connection = "scm:git:git://github.com/singularhealth/3cr-sdk-android"
                        developerConnection = "scm:git:ssh://github.com/singularhealth/3cr-sdk-android.git"
                        url = "https://github.com/singularhealth/3cr-sdk-android"
                    }
                    withXml {
                        val depsNode  = asNode().appendNode("dependencies")

                        val artifact = arrayOf("jackson-databind", "jackson-annotations")

                        artifact.forEach {
                            val depNode  = depsNode.appendNode("dependency")
                            depNode.appendNode("groupId", "com.fasterxml.jackson.core")
                            depNode.appendNode("artifactId", it)
                            depNode.appendNode("version", "2.14.0")
                        }

                    }
                }
            }
        }
        repositories {
            maven {
                name = "LocalBuildDependency"
                println(uri("${project.rootProject.projectDir.path}/build/repo"))
                url = uri("${project.rootProject.projectDir.path}/build/repo")
            }
            maven {
                name = "GitHubPackages"
                url = URI("https://maven.pkg.github.com/singularhealth/3cr-sdk-android")
                credentials {
                    val properties = Properties()
                    properties.load(project.rootProject.file("local.properties").inputStream())

                    username = properties["USERNAME"].toString()
                    password = properties["TOKEN"].toString()
                }
            }
        }
    }
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    githubRelease {
        val changelogFile = project.findProperty("changelog")
        println(properties)
        apiToken = properties["TOKEN"].toString()

        repository = "singularhealth/3cr-sdk-android"
        releaseName = "v$version"
        releaseBody = "Updated Release version for @3cr/sdk-android: v$version"
        tagName = "v$version"
        println(project.rootProject.projectDir.path)

        artifacts.from(
//            "${project.rootProject.projectDir.path}/build/repo/com/github/singularhealth/3cr-sdk-android/$version/*",
            "${project.rootProject.projectDir.path}/build/repo/com/github/singularhealth/3cr-sdk-android/$version/3cr-sdk-android-$version.aar",
            "${project.rootProject.projectDir.path}/build/repo/com/github/singularhealth/3cr-sdk-android/$version/3cr-sdk-android-$version.aar.md5",
            "${project.rootProject.projectDir.path}/build/repo/com/github/singularhealth/3cr-sdk-android/$version/3cr-sdk-android-$version.aar.sha1",
            "${project.rootProject.projectDir.path}/build/repo/com/github/singularhealth/3cr-sdk-android/$version/3cr-sdk-android-$version.aar.sha256",
            "${project.rootProject.projectDir.path}/build/repo/com/github/singularhealth/3cr-sdk-android/$version/3cr-sdk-android-$version.aar.sha512",
            "${project.rootProject.projectDir.path}/build/repo/com/github/singularhealth/3cr-sdk-android/$version/3cr-sdk-android-$version.pom",
            "${project.rootProject.projectDir.path}/build/repo/com/github/singularhealth/3cr-sdk-android/$version/3cr-sdk-android-$version.pom.md5",
            "${project.rootProject.projectDir.path}/build/repo/com/github/singularhealth/3cr-sdk-android/$version/3cr-sdk-android-$version.pom.sha1",
            "${project.rootProject.projectDir.path}/build/repo/com/github/singularhealth/3cr-sdk-android/$version/3cr-sdk-android-$version.pom.sha256",
            "${project.rootProject.projectDir.path}/build/repo/com/github/singularhealth/3cr-sdk-android/$version/3cr-sdk-android-$version.pom.sha512",
//            "${project.rootProject.projectDir.path}/build/repo/com/github/singularhealth/3cr-sdk-android/$version/3cr-sdk-android-$version.pom"
        )
    }

//}

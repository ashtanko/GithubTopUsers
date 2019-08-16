// Top-level build file where you can add configuration options common to all sub-projects/modules.
allprojects {
    repositories {
        google()
        jcenter()
    }
}

buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://maven.fabric.io/public")
    }
    dependencies {
        classpath(Dependencies.App.GRADLE_PLUGIN)
        classpath(Dependencies.Kotlin.Build.KOTLIN_GRADLE_PLUGIN)
        classpath(Dependencies.App.GOOGLE_SERVICES)
        classpath(Dependencies.App.FABRIC)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://maven.fabric.io/public")
    }
}
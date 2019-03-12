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
    }
    dependencies {
        classpath(Dependencies.gradlePlugin)
        classpath(Dependencies.Kotlin.Build.gradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.detekt

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(Dependencies.App.GRADLE_PLUGIN)
        classpath(Dependencies.Kotlin.Build.KOTLIN_GRADLE_PLUGIN)
        classpath(Dependencies.App.GOOGLE_SERVICES)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = Repositories.JITPACKIO_URL)
    }
}

plugins {
    id(Plugins.DETEKT_PLUGIN_ID) version Versions.DETEKT_VERSION
    id(Plugins.KTLINT_IDEA_PLUGIN_ID) version Versions.KTLINT_VERSION
}

detekt {
    toolVersion = Versions.DETEKT_VERSION
    input = files("app/src/main/java", "core/src/main/java", "buildSrc/src/main/kotlin")
    config = files("$rootDir/default-detekt-config.yml")
    reports {
        xml {
            enabled = true
            destination = file("$buildDir/reports/detekt-report.xml")
        }
        html {
            enabled = true
            destination = file("$buildDir/reports/detekt-report.html")
        }
    }
}

tasks.withType<Detekt> {
    include("**/*.kt")
    include("**/*.kts")
    exclude(".*/resources/.*")
    exclude(".*/build/.*")
    exclude("/versions.gradle.kts")
    exclude("buildSrc/settings.gradle.kts")
}

//// Top-level build file where you can add configuration options common to all sub-projects/modules.
//allprojects {
//    repositories {
//        google()
//        jcenter()
//    }
//}
//
//buildscript {
//    repositories {
//        google()
//        jcenter()
//        maven(url = "https://plugins.gradle.org/m2/")
//        maven(url = "https://maven.fabric.io/public")
//    }
//    dependencies {
//        classpath(Dependencies.App.GRADLE_PLUGIN)
//        classpath(Dependencies.Kotlin.Build.KOTLIN_GRADLE_PLUGIN)
//        classpath(Dependencies.App.GOOGLE_SERVICES)
//        classpath(Dependencies.App.FABRIC)
//    }
//}
//
//allprojects {
//    repositories {
//        google()
//        jcenter()
//        maven(url = "https://plugins.gradle.org/m2/")
//        maven(url = "https://maven.fabric.io/public")
//    }
//}
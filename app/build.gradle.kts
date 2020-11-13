import Dependencies.AndroidSdk.ANDROID_SECURITY_CRYPTO
import Dependencies.Facebook.applyLithoDependencies
import Dependencies.Kotlin.COROUTINES
import Dependencies.Kotlin.COROUTINES_ANDROID
import Dependencies.Kotlin.STDLIB
import Dependencies.Lifecycle.EXTENSIONS
import Dependencies.Lifecycle.KTX
import Dependencies.Lifecycle.LIVEDATA
import Dependencies.Lifecycle.VIEWMODEL
import Dependencies.Other.STATE_DELEGATOR
import Dependencies.RX.addLatestRxJavaWithAndroid
import Dependencies.RX.addRxJavaWithAndroid
import Versions.Android.Build.COMPILE_SDK_VERSION
import Versions.Android.Build.MIN_SDK_VERSION
import Versions.Android.Build.TARGET_SDK_VERSION
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.io.FileInputStream
import java.io.FileWriter
import java.util.Properties
import java.util.regex.Pattern

plugins {
    id(ANDROID_APP_PLUGIN_ID)
    id(Plugins.KTLINT_PLUGIN_ID)
    kotlin(KOTLIN_ANDROID_PLUGIN_ID)
    kotlin(KOTLIN_APT_PLUGIN_ID)
}

ktlint {
    android.set(true)
}

val releasePropertiesFile = "release.properties"
val localProps = Properties()

val versionPropsFileName = "release.properties"

android {

    val versionPropsFile = file("version.properties")
    if (!versionPropsFile.canRead()) {
        println("Could not read version.properties, please, provide this file with VERSION_NUMBER parameter")
    }

    val versionProps = Properties()
    versionProps.load(FileInputStream(versionPropsFile))

    var versionNumber = versionProps["VERSION_CODE"].toString().toInt()

    val propFile = rootProject.file(releasePropertiesFile)

    val storeFileId = "STORE_FILE"
    val storePasswordId = "STORE_PASSWORD"
    val aliasId = "ALIAS"
    val passwordId = "PASSWORD"

    var storeFilePath = "${properties[storeFileId]}"
    var storePass = "${properties[storePasswordId]}"
    var alias = "${properties[aliasId]}"
    var password = "${properties[passwordId]}"


    if (propFile.exists()) {
        localProps.load(FileInputStream(propFile))
        val storeFilePathString = localProps[storeFileId].toString()

        if (storeFilePathString.isNotEmpty()) {
            storeFilePath = storeFilePathString
        }

        val storePasswordString = localProps[storePasswordId].toString()
        if (storePasswordString.isNotEmpty()) {
            storePass = storePasswordString
        }

        val aliasString = localProps[aliasId].toString()
        if (aliasString.isNotEmpty()) {
            alias = aliasString
        }

        val passwordString = localProps[passwordId].toString()
        if (passwordString.isNotEmpty()) {
            password = passwordString
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    compileSdkVersion(COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = "dev.blank"
        minSdkVersion(MIN_SDK_VERSION)
        targetSdkVersion(TARGET_SDK_VERSION)
        versionCode = versionNumber
        versionName = versionProps["VERSION_NAME"].toString()

        // buildConfigField(
        //     "String",
        //     "ENDPOINT",
        //     "\"wss://dev\""
        // )
        // buildConfigField("String", "DEV_ENDPOINT", "\"wss://dev\"")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments(mapOf(("room.schemaLocation" to "$projectDir/schemas")))
            }
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        resConfig("en")
    }

    lintOptions {
        isAbortOnError = false
        isWarningsAsErrors = true
        isCheckDependencies = true
        isIgnoreTestSources = true
        setLintConfig(file("lint.xml"))
        disable("GradleDeprecated")
        disable("OldTargetApi")
        disable("GradleDependency")
    }

    tasks.withType<JavaCompile> {
        options.isIncremental = true
    }

    signingConfigs {
        getByName(DEBUG_BUILD_TYPE) {
            println(DEBUG_BUILD_TYPE)
        }

        create(RELEASE_BUILD_TYPE) {
            storeFile = file(storeFilePath)
            storePassword = storePass
            keyAlias = alias
            keyPassword = password
        }
    }

    // flavorDimensions("default")
    //
    // productFlavors {
    //     create("prod") {
    //         setDimension("default")
    //         applicationId = "..."
    //     }
    //
    //     create("dev") {
    //         setDimension("default")
    //         applicationId = "..."
    //     }
    // }
    //
    // variantFilter {
    //     val names = flavors.map { it.name + buildType.name }
    //     //println("NAMES: $names")
    //
    //     if (names.contains("devrelease")) {
    //         setIgnore(true)
    //     }
    // }

    buildTypes {
        getByName(DEBUG_BUILD_TYPE) {
            signingConfig = signingConfigs.getByName(DEBUG_BUILD_TYPE)
        }

        getByName(RELEASE_BUILD_TYPE) {
            signingConfig = signingConfigs.getByName(RELEASE_BUILD_TYPE)
            //isUseProguard = true
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = true
            isZipAlignEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android.txt")
            )
            proguardFiles(file("proguard-rules.pro"))
            proguardFiles(file("proguard.cfg"))
            proguardFiles(file("proguard-gson.pro"))
            proguardFiles(file("proguard-dagger.pro"))
            proguardFiles(file("proguard-okhttp.pro"))
            proguardFiles(file("proguard-eventbus.pro"))
        }
    }

    dataBinding {
        isEnabled = true
    }

    bundle {
        language {
            enableSplit = true
        }

        density {
            enableSplit = true
        }

        abi {
            enableSplit = true
        }
    }

    lintOptions {
        isAbortOnError = false
    }

    tasks {
        val incrementBuildNumberTask by registering(DefaultTask::class) {
            versionNumber += 1
            versionProps.store(FileWriter("app/version.properties"), null)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        // "this" is currently lacking a proper type
        // See: https://youtrack.jetbrains.com/issue/KT-31077
        val options = this as? KotlinJvmOptions
        options?.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

}

dependencies {

    //region kotlin
    implementation(kotlin(STDLIB))
    implementation(COROUTINES)
    implementation(COROUTINES_ANDROID)
    //endregion

    //region android sdk
    implementation(Dependencies.AndroidSdk.APPCOMPAT)
    implementation(Dependencies.AndroidSdk.RECYCLER_VIEW)
    implementation(Dependencies.AndroidSdk.CONSTRAINT_LAYOUT)
    implementation(Dependencies.AndroidSdk.MATERIAL)
    implementation(Dependencies.AndroidSdk.CARD_VIEW)
    implementation(Dependencies.AndroidSdk.ANNOTATION)
    implementation(Dependencies.AndroidSdk.KTX_CORE)
    implementation(Dependencies.AndroidSdk.FRAGMENT_RUNTIME)
    implementation(Dependencies.AndroidSdk.FRAGMENT_KTX)
    testImplementation(Dependencies.AndroidSdk.FRAGMENT_TESTING)
    implementation(Dependencies.AndroidSdk.ACTIVITY_KTX)

    //implementation(Dependencies.AndroidSdk.PAGING)
    implementation(STATE_DELEGATOR)
    //implementation(ANDROID_SECURITY_CRYPTO)

    implementation(Dependencies.ArchCore.RUMTIME)
    testImplementation(Dependencies.ArchCore.TESTING)
    //endregion

    //region lifecycle
    implementation(LIVEDATA)
    implementation(EXTENSIONS)
    implementation(VIEWMODEL)
    implementation(KTX)
    kapt(Dependencies.Lifecycle.COMPILER)
    //endregion

    //addRxJavaWithAndroid(Dependencies.RX.Version.TWO)

    //region dependency injection
    //implementation(DAGGER)
    //kapt(Dependencies.Injection.COMPILER)
    //endregion

    //region image processor
    //implementation(Dependencies.ImageProcessor.COIL)
    //implementation(Dependencies.ImageProcessor.CROPPER)
    //implementation(Dependencies.ImageProcessor.COMPRESSOR)
    //endregion

    //region other
    //implementation(Dependencies.Other.ROOTBEER)
    //implementation("androidx.preference:preference-ktx:1.1.0")
    //debugImplementation(Dependencies.Other.LEAKCANARY)

    // NOTE For Android 14+ support, downgrade zxing:core to 3.3.0 or earlier
    //implementation(Dependencies.Other.ZXING)
    //implementation(Dependencies.Other.ZXING_EMBEDDED) { isTransitive = false }
    //implementation(Dependencies.Other.EVENT_BUS)
    //implementation(Dependencies.Other.ALERTER)

    //implementation(Dependencies.RX.RX_ANDROID_BLE)
    //implementation(Dependencies.RX.RX_ANDROID)
    //implementation(Dependencies.RX.RX_REPLAYING_SHARE)
    //implementation(Dependencies.Other.SEGMENTED_CONTROL)
    //endregion

    //region analytics
    //implementation(Dependencies.Other.APP_CENTER_ANALYTICS)
    //implementation(Dependencies.Other.APP_CENTER_CRASHES)
    //endregion

    //implementation(Dependencies.Network.OKHTTP)
    //implementation(Dependencies.Network.GSON)

    //region db
    //implementation(Dependencies.Room.RUNTIME)
    //implementation(Dependencies.Room.COROUTINES)
    //kapt(Dependencies.Room.COMPILER)
    //endregion

    //region modules
    //endregion

    //region navigation
    //implementation(Dependencies.Other.FRAGMENT_HELPER)
    //endregion

    //region work manager
    //implementation(Dependencies.Work.RUNTIME)
    //implementation(Dependencies.Work.RUNTIME_KTX)
    //region

    applyLithoDependencies()

    //region test dependencies
    // androidTestUtil(TestDependencies.ORCHESTRATOR)
    // androidTestImplementation(TestDependencies.RUNNER)
    // androidTestImplementation(TestDependencies.RULES)
    // androidTestImplementation(TestDependencies.ROOM)
    // androidTestImplementation(TestDependencies.ESPRESSO)
    // androidTestImplementation(TestDependencies.Mockito.CORE)
    // androidTestImplementation(TestDependencies.Mockito.KOTLIN_2)
    // androidTestImplementation(TestDependencies.KOTLIN_REFLECT)
    // androidTestImplementation(TestDependencies.ARCH_CORE)
    // androidTestImplementation(TestDependencies.JUNIT)
    //
    // testImplementation(TestDependencies.ARCH_CORE)
    // testImplementation(TestDependencies.JUNIT)
    // testImplementation(TestDependencies.Mockito.CORE)
    // testImplementation(TestDependencies.Mockito.KOTLIN_2)
    //endregion
}

open class RemoveAppTask : DefaultTask() {

    @TaskAction
    fun removeApp() {
        Runtime.getRuntime().exec("adb uninstall dev.blank")
    }
}

open class IncreaseVersionCode : DefaultTask() {

    companion object {
        private const val VERSION_GROUP = "VERSION_CODE="
        private const val VERSION_FILE = "version.properties"
    }

    @TaskAction
    fun increase() {
        val versionFile = File(VERSION_FILE)
        val pattern = Pattern.compile("$VERSION_GROUP\\s+(\\d+)")
        val versionFileText = versionFile.readText()
        val matcher = pattern.matcher(versionFileText)
        matcher.find()
        val versionCode = Integer.parseInt(matcher.group(1))
        val buildContent = matcher.replaceAll("$VERSION_GROUP " + versionCode.inc())
        versionFile.writeText(buildContent)
    }
}

tasks.register<RemoveAppTask>("removeAppTask")
tasks.register<IncreaseVersionCode>("bumpVersionCode")
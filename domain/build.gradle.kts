plugins {
    id("kotlin")
}

dependencies {
    //region kotlin
    implementation(Dependencies.Kotlin.Stdlib.CORE)
    implementation(Dependencies.Kotlin.COROUTINES)
    implementation(Dependencies.Kotlin.COROUTINES_ANDROID)
    //endregion

    implementation(Dependencies.Injection.DAGGER)

    implementation(project(Modules.COMMON_KOTLIN))

    testImplementation(TestDependencies.JUNIT)
    testImplementation(TestDependencies.MOCKITO)
    testImplementation(TestDependencies.JUNIT_KOTLIN)
    testImplementation(TestDependencies.MOCKITO_KOTLIN)
    testImplementation(TestDependencies.MOCKITO_KOTLIN_2)
    testImplementation(Dependencies.Kotlin.COROUTINES)
    testImplementation(Dependencies.Kotlin.Stdlib.CORE)

}
plugins {
    id("kotlin")
}

dependencies {
    //region kotlin
    implementation(Dependencies.Kotlin.Stdlib.core)
    implementation(Dependencies.Kotlin.coroutines)
    //endregion

    implementation(Dependencies.dagger)

    implementation(project(":commonKotlin"))

    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.mockito)
    testImplementation(TestDependencies.kotlinTest)
    testImplementation(TestDependencies.mockitoKotlin)
    testImplementation(TestDependencies.mockitokotlin2)
    testImplementation(Dependencies.Kotlin.coroutines)
    testImplementation(Dependencies.Kotlin.Stdlib.core)

}
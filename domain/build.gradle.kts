plugins {
    id("java-library")
    id("kotlin")

    jacoco
}

addKtlint()

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("javax.inject:javax.inject:${Versions.JAVAX_INJECT_VERSION}")

    // Testing
    addTestImplementationBaseLib()
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLINX_COROUTINES_VERSION}")
}

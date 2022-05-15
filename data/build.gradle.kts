plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("plugins.jacoco-report")
}

addKtlint()

val secrets = rootDir.loadGradleProperties("secrets.properties")

fun com.android.build.api.dsl.BuildType.addStringEnv(key: String, value: String) {
    buildConfigField("String", key, value)
}
fun com.android.build.api.dsl.BuildType.addApiKey(value: String) {
    addStringEnv("API_KEY", value)
}

fun com.android.build.api.dsl.BuildType.addApiSecret(value: String) {
    addStringEnv("API_SECRET", value)
}

fun com.android.build.api.dsl.BuildType.addDefaultSecret() {
    addApiKey(secrets["clientId"] as String)
    addApiSecret(secrets["clientSecret"] as String)
}

android {
    compileSdk = Versions.ANDROID_COMPILE_SDK_VERSION
    defaultConfig {
        minSdk = Versions.ANDROID_MIN_SDK_VERSION
        targetSdk = Versions.ANDROID_TARGET_SDK_VERSION
        versionCode = Versions.ANDROID_VERSION_CODE
        versionName = Versions.ANDROID_VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")

            addDefaultSecret()
        }

        getByName(BuildType.DEBUG) {
            /**
             * From AGP 4.2.0, Jacoco generates the report incorrectly, and the report is missing
             * some code coverage from module. On the new version of Gradle, they introduce a new
             * flag [testCoverageEnabled], we must enable this flag if using Jacoco to capture
             * coverage and creates a report in the build directory.
             * Reference: https://developer.android.com/reference/tools/gradle-api/7.1/com/android/build/api/dsl/BuildType#istestcoverageenabled
             */
            isTestCoverageEnabled = true

            addDefaultSecret()
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    lintOptions {
        isCheckDependencies = true
        xmlReport = true
        xmlOutput = file("build/reports/lint/lint-result.xml")
    }
}

dependencies {
    implementation(project(Module.DOMAIN))

    implementation("androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX_VERSION}")
    implementation("androidx.security:security-crypto:${Versions.ANDROID_CRYPTO_VERSION}")
    implementation("com.google.dagger:hilt-android:${Versions.HILT_VERSION}")
    implementation("com.squareup.moshi:moshi:${Versions.MOSHI_VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}")
    implementation("javax.inject:javax.inject:${Versions.JAVAX_INJECT_VERSION}")

    addRoom()

    api("com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT_VERSION}")
    api("com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}")

    api("com.squareup.moshi:moshi-adapters:${Versions.MOSHI_VERSION}")
    api("com.squareup.moshi:moshi-kotlin:${Versions.MOSHI_VERSION}")

    api("com.squareup.okhttp3:okhttp:${Versions.OKHTTP_VERSION}")
    api("com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_VERSION}")

    addKotlinCoroutine()

    // Testing
    addTestImplementationBaseLib()
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLINX_COROUTINES_VERSION}")
}

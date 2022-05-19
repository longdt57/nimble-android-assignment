import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register

fun Project.addKtlint() {
    val ktlint = configurations.create("ktlint")
    dependencies {
        add("ktlint", "com.pinterest:ktlint:0.45.2")
    }

    project.tasks.register<JavaExec>("ktlint") {
        group = "verification"
        description = "Check Kotlin code style."
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("--android", "src/**/*.kt")
    }

    tasks.register<JavaExec>("ktlintFormat") {
        group = "formatting"
        description = "Fix Kotlin code style deviations."
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("--android", "-F", "src/**/*.kt")
    }
}

fun DependencyHandler.addKotlinCoroutine() {
    add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES_VERSION}")
}

fun DependencyHandler.addHilt() {
    add("implementation", "com.google.dagger:hilt-android:${Versions.HILT_VERSION}")
    add("kapt", "com.google.dagger:hilt-compiler:${Versions.HILT_VERSION}")
}

fun DependencyHandler.addRoom() {
    add("implementation", "androidx.room:room-runtime:${Versions.ROOM_VERSION}")
    add("implementation", "androidx.room:room-ktx:${Versions.ROOM_VERSION}")
    add("annotationProcessor", "androidx.room:room-compiler:${Versions.ROOM_VERSION}")
    add("kapt", "androidx.room:room-compiler:${Versions.ROOM_VERSION}")
}

fun DependencyHandler.addTestImplementationBaseLib(configuration: String = "testImplementation") {
    add(configuration, "junit:junit:${Versions.TEST_JUNIT_VERSION}")
    add(configuration, "io.mockk:mockk:${Versions.TEST_MOCKK_VERSION}")
    add(configuration, "io.kotest:kotest-assertions-core:${Versions.TEST_KOTEST_VERSION}")
    add(configuration, "org.mockito:mockito-core:${Versions.TEST_MOCKITO}")
    add(configuration, "org.mockito.kotlin:mockito-kotlin:${Versions.TEST_MOCKITO_KOTLIN}")
}

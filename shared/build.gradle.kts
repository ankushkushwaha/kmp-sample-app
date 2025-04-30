import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation("org.koin:koin-core:3.2.0")

            implementation("io.ktor:ktor-client-core:2.3.1")
            implementation("io.ktor:ktor-client-json:2.3.1")
            implementation("io.ktor:ktor-client-serialization:2.3.1")
            implementation("org.koin:koin-core:3.2.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation("io.ktor:ktor-client-android:2.3.1")
            implementation("org.koin:koin-android:3.2.0")
        }

        iosMain.dependencies {
            implementation("io.ktor:ktor-client-ios:2.3.1")
        }
    }
}

android {
    namespace = "com.example.kmp_sample_app"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

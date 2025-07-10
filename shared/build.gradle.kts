import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqlDelight)

}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
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
            baseName = "Shared"
            isStatic = true
        }
    }

    sourceSets {

        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
        commonMain.dependencies {
            api(libs.kmp.observableviewmodel.core)

            implementation(libs.koin.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.client.logging )
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.multiplatform.settings)
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")

            implementation(libs.sqldelight.coroutines)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.koin.android)
            implementation(libs.sqldelight.android)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
        }

//        androidUnitTest.dependencies {
//            implementation(libs.kotlin.test)
//            implementation("junit:junit:4.13.2")
//            implementation("io.mockk:mockk:1.11.0")
//            implementation("io.ktor:ktor-client-mock:2.3.8")
//            implementation("net.bytebuddy:byte-buddy:1.10.21")
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
//            implementation("app.cash.turbine:turbine:1.1.0")
//        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native)
        }

//        iosTest.dependencies {
//            implementation(libs.kotlin.test)
//            implementation("io.ktor:ktor-client-mock:2.3.8") // Add this
//        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName = "com.example.kmp_sample_app.database"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.android)
}


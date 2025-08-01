import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
//    alias(libs.plugins.kotlinSerialization)
//    alias(libs.plugins.sqlDelight)

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
            baseName = "CalculatorModule"
            isStatic = true
        }
    }

    sourceSets {

        commonMain.dependencies {
            // Add dependencies here
        }
    }
}

//sqldelight {
//    databases {
//        create("AppDatabase") {
//            packageName = "com.example.kmp_sample_app.database"
//        }
//    }
//}
android {
    namespace = "com.example.caclulator_module"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
//dependencies {
//    implementation(libs.androidx.lifecycle.viewmodel.android)
//}


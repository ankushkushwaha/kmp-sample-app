import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    id("jacoco")
}

android {
    namespace = "com.example.kmp_sample_app.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.kmp_sample_app.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compat)
    implementation(libs.koin.androidx.compose)
    implementation(project(":calculatorModule"))
    testImplementation(kotlin("test"))

    debugImplementation(libs.compose.ui.tooling)
}

tasks.register<JacocoReport>("jacocoAndroidAppTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        // ✅ Custom name for JaCoCo XML
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/androidApp-report.xml"))
        html.required.set(true)
    }

    val debugTree = fileTree("${layout.buildDirectory.get()}/tmp/kotlin-classes/debug")
    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(file("${layout.buildDirectory.get()}/jacoco/testDebugUnitTest.exec"))
}

import org.gradle.api.tasks.Exec

val sharedReportDir = layout.projectDirectory.dir("shared/build/reports/jacoco").asFile
val androidReportDir = layout.projectDirectory.dir("androidApp/build/reports/jacoco").asFile

val sharedReport = layout.projectDirectory.file("shared/build/reports/jacoco/report.xml").asFile
val androidReport = layout.projectDirectory.file("androidApp/build/reports/jacoco/androidApp-report.xml").asFile

// 1. Clean old android report files
tasks.register("cleanAndroidCoverageReports") {
    doLast {
        println("🧹 Deleting old Jacoco reports...")
        delete(sharedReportDir)
        delete(androidReportDir)
    }
}

tasks.register<Exec>("androidCoverage") {
    group = "verification"
    description = "Coverage for androidApp + shared"

    dependsOn(
        ":shared:jacocoSharedTestReport",
        ":androidApp:jacocoAndroidAppTestReport"
    )

    commandLine(
        "sonar-scanner",
        "-Dsonar.projectName=kmp-sample-app-android",
        "-Dsonar.projectKey=kmp-sample-app-android",
        "-Dsonar.host.url=http://localhost:9000/",
        "-Dsonar.token=squ_2d9c43170fad5399f84e49f612763365466ca3ef",
        "-Dsonar.sources=androidApp/src/main,shared/src/commonMain",
        "-Dsonar.tests=androidApp/src/test,shared/src/commonTest",
        "-Dsonar.coverage.jacoco.xmlReportPaths=${sharedReport.absolutePath},${androidReport.absolutePath}",
        "-Dsonar.exclusions=**/iosApp/**,**/Pods/**,**/*.xml",
//        "-Dsonar.branch.name=$branchName"
    )
}


tasks.register("iosCoverage") {
    group = "verification"
    description = "Coverage"

    doLast {

        val xcresult = file("iosApp/build/iosTestResult.xcresult")
        val swiftCoverageXml = file("iosApp/ios-swift-coverage.xml")
        val kotlinCoverageXml = file("shared/build/reports/jacoco/report.xml") // ✅ JaCoCo XML output

        xcresult.deleteRecursively()
        swiftCoverageXml.delete()
        kotlinCoverageXml.delete()

        exec {
            commandLine = listOf("./gradlew", ":shared:testDebugUnitTest", ":shared:jacocoTestReport")
        }

        exec {
            commandLine = listOf(
                "xcodebuild",
                "-quiet",
                "-project", "iosApp/iosApp.xcodeproj",
                "-scheme", "iosApp",
                "-sdk", "iphonesimulator",
                "-destination", "platform=iOS Simulator,name=iPhone 16,OS=latest",
                "-enableCodeCoverage", "YES",
                "-resultBundlePath", xcresult.absolutePath,
                "clean", "test"
            )
        }

        val converterScript = file("xccov-to-sonarqube-generic.sh")
        if (!converterScript.exists()) {
            exec {
                commandLine = listOf(
                    "curl", "-O", "https://raw.githubusercontent.com/SonarSource/sonar-scanning-examples/master/swift-coverage/swift-coverage-example/xccov-to-sonarqube-generic.sh"
                )
            }
            exec {
                commandLine = listOf("chmod", "+x", converterScript.absolutePath)
            }
        }
        exec {
            commandLine = listOf("./xccov-to-sonarqube-generic.sh", xcresult.absolutePath)
            standardOutput = swiftCoverageXml.outputStream()
        }
        exec {
            commandLine = listOf("sed", "-i", "", "s|<file path=\\\"iosApp/|<file path=\\\"iosApp/iosApp/|g", swiftCoverageXml.absolutePath)
        }

        exec {
            commandLine = listOf(
                "sonar-scanner",
                "-Dsonar.projectName=kmp-sample-app-iOS",
                "-Dsonar.projectKey=kmp-sample-app-ios",
                "-Dsonar.host.url=http://localhost:9000/",
                "-Dsonar.token=squ_2d9c43170fad5399f84e49f612763365466ca3ef",
                "-Dsonar.sources=iosApp/iosApp,shared/src/commonMain",
                "-Dsonar.tests=shared/src/commonTest",
                "-Dsonar.coverage.jacoco.xmlReportPaths=${kotlinCoverageXml.absolutePath}",
                "-Dsonar.coverageReportPaths=${swiftCoverageXml.absolutePath}",
                "-Dsonar.exclusions=**/Pods/**,**/androidApp/**,**/*.xml",
//                "-Dsonar.branch.name=$sonarBranchName"
            )
        }
    }
}
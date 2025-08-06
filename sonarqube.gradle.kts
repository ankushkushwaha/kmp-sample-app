import org.gradle.api.tasks.Exec

val sharedReport = layout.projectDirectory.file("shared/build/reports/jacoco/report.xml").asFile
val androidReport = layout.projectDirectory.file("androidApp/build/reports/jacoco/androidApp-report.xml").asFile

tasks.register<Exec>("androidCoverage") {
    group = "verification"
    description = "Coverage for androidApp + shared"

    dependsOn(
        ":shared:jacocoSharedTestReport",
        ":androidApp:jacocoAndroidAppTestReport"
    )

    commandLine(
        "sonar-scanner",
        "-Dsonar.projectKey=kmp-sample-app",
        "-Dsonar.host.url=http://localhost:9000/",
        "-Dsonar.token=squ_2d9c43170fad5399f84e49f612763365466ca3ef",
        "-Dsonar.sources=androidApp/src/main,shared/src/commonMain",
        "-Dsonar.tests=androidApp/src/test,shared/src/commonTest",
        "-Dsonar.coverage.jacoco.xmlReportPaths=${sharedReport.absolutePath},${androidReport.absolutePath}",
        "-Dsonar.exclusions=**/iosApp/**,**/Pods/**,**/*.xml",
//        "-Dsonar.branch.name=$branchName"

    )
}

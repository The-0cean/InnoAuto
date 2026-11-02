plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))

    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.junit.jupiter:junit-jupiter-params")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.assertj:assertj-core:3.25.3")

    implementation("io.rest-assured:rest-assured:5.5.6")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging{
        events("PASSED", "SKIPPED", "FAILED", "STANDARD_OUT")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStandardStreams=true
    }
}

tasks.register<Test>("runAllTests"){
    useJUnitPlatform() {excludeTags("failing")}
    testClassesDirs= sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    description = "Runs all tests of the Day 2 task 1"
    group = "Verification"
    testLogging{
        events("PASSED", "SKIPPED", "FAILED", "STANDARD_OUT")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStandardStreams = true
    }
}

tasks.register("printTestRunOver"){
    description = "Prints \"Test run is over\" after tests finish"
    group= "Verification"
    doLast{
        println("Test run is over")
    }
}

tasks.named("runAllTests"){
    finalizedBy("printTestRunOver")
}

tasks.register<Test>("runFailingDemo") {
    useJUnitPlatform {
        includeTags("failing")
    }
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    description = "Демонстрация информативного падения ассерта"
    group = "verification"
    ignoreFailures = true
    testLogging {
        events("PASSED", "FAILED", "STANDARD_OUT")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStandardStreams = true
    }

}

tasks.register<Test>("runApiTests") {
    useJUnitPlatform {
        includeTags("api")                                   // только тесты с @Tag("api")
    }
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    description = "Runs only API tests (server must be running on localhost:8080)"
    group = "verification"
    testLogging {
        events("PASSED", "SKIPPED", "FAILED")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
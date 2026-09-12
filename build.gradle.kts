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
    useJUnitPlatform()
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
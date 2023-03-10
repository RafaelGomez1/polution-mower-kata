import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("com.diffplug.spotless") version "6.12.0"
    id("org.springframework.boot") version "2.7.2"
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    id("idea")
    kotlin("plugin.spring") version "1.7.21"
    application
}

group = "com.codely"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

sourceSets {
    create("test-integration") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val testIntegrationImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}

configurations["testIntegrationRuntimeOnly"].extendsFrom(configurations.testRuntimeOnly.get())

val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"
    testClassesDirs = sourceSets["test-integration"].output.classesDirs
    classpath = sourceSets["test-integration"].runtimeClasspath
    useJUnitPlatform()
    shouldRunAfter("test")
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Spring Boot
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.12.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Logging
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    // Axon
    implementation("org.axonframework:axon-messaging:4.6.3")
    implementation("org.axonframework:axon-spring-boot-starter:4.6.3") {
        exclude(group = "org.axonframework", module = "axon-server-connector")
    }

    // Polyline
    implementation("com.google.maps:google-maps-services:2.1.2")
    implementation("org.gavaghan:geodesy:1.1.3")

    // Functional Programming
    implementation("io.arrow-kt:arrow-core:1.1.4-rc.3")
    implementation("io.arrow-kt:arrow-optics:1.1.4-rc.3")
    implementation("io.arrow-kt:arrow-fx-coroutines:1.1.2")
    implementation("io.arrow-kt:arrow-fx-stm:1.1.2")

    // Quartz Scheduler
    implementation("org.springframework.boot:spring-boot-starter-quartz")

    // Testing
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.3.0") {
        because("provides good testing for arrow")
    }

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-test")

    // Coroutines
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xcontext-receivers")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    kotlin {
        ktlint()
    }
    kotlinGradle {
        ktlint()
    }
}

tasks.check {
    dependsOn(integrationTest)
    dependsOn(tasks.spotlessCheck)
}

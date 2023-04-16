plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "bob"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}
dependencies {
    implementation("io.prometheus:simpleclient_pushgateway:0.12.0")
    implementation("ch.qos.logback:logback-classic:1.2.9")
    implementation("io.micrometer:micrometer-core:1.8.0")
    implementation("io.micrometer:micrometer-registry-prometheus:1.8.0")
    implementation("io.github.mweirauch:micrometer-jvm-extras:0.2.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}
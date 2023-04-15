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
    implementation ("io.prometheus:simpleclient:0.12.0")
    implementation ("io.prometheus:simpleclient_hotspot:0.12.0")
    implementation ("io.prometheus:simpleclient_logback:0.12.0")
    implementation ("ch.qos.logback:logback-classic:1.2.9")
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
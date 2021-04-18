import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val exposedVersion = "0.30.1"

val compileKotlin: KotlinCompile by tasks

plugins {
    application
    kotlin("jvm") version "1.4.32"
}

group = "xyz.thetbw"
version = "0.0.1"
application {
    mainClass.set("xyz.thetbw.ApplicationKt")
}

repositories {
    mavenCentral()
}

//编译选项
compileKotlin.kotlinOptions{
    jvmTarget = "1.8"
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-gson:$ktor_version")
    implementation("io.ktor:ktor-freemarker:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")

    //exposed依赖
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    //数据库连接池 HikariCP
    implementation("com.zaxxer:HikariCP:4.0.3")

    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")

    implementation("com.h2database:h2:1.4.199")
    implementation("org.kodein.di:kodein-di:7.5.0")
}
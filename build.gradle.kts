import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val exposedVersion = "0.30.1"

val compileKotlin: KotlinCompile by tasks
val compileTestKotlin: KotlinCompile by tasks

plugins {
    application
    kotlin("jvm") version "1.4.32"
    kotlin("kapt") version "1.4.20"
    id("io.ebean").version("12.8.0")
//    kotlin("plugin.serialization") version "1.5.20"
}

ebean {
    debugLevel = 0
}

group = "xyz.thetbw"
version = "0.0.1"
application {
    mainClass.set("xyz.thetbw.ApplicationKt")
}

repositories {
    // 依赖使用阿里云 maven 源
    maven {
        setUrl("https://maven.aliyun.com/repository/public/")
    }
    mavenLocal()
    mavenCentral()
}

//编译选项
compileKotlin.kotlinOptions{
    jvmTarget = "1.8"
}

compileTestKotlin.kotlinOptions{
    jvmTarget = "1.8"
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-freemarker:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")  //日志
    implementation("io.ktor:ktor-auth:$ktor_version")  //身份验证
    implementation("io.ktor:ktor-auth-jwt:$ktor_version") //jwt身份验证
    implementation("io.ktor:ktor-gson:$ktor_version") //序列化

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")

    //数据库连接池 HikariCP
    implementation("com.zaxxer:HikariCP:4.0.3")

    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")

    implementation("com.h2database:h2:1.4.199")
    implementation("org.kodein.di:kodein-di:7.5.0")

    //导入ebean
    implementation("io.ebean:ebean:12.8.0")
    testImplementation("io.ebean:ebean-test:12.8.0")
    //用于生成 ebean Query beans ，这里暂时不用这个
//    kapt("io.ebean:kotlin-querybean-generator:12.8.0")

    implementation("cn.hutool:hutool-all:5.7.7")
    implementation("redis.clients:jedis:3.6.3")

}
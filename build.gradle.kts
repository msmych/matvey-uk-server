import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"
}

repositories {
    mavenCentral()
}

val kotlinxSerializationVersion: String by project
val logbackVersion: String by project
val microutilsVersion: String by project
val jupiterVersion: String by project
val mockkVersion: String by project
val assertJVersion: String by project

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")

        implementation("io.github.microutils:kotlin-logging-jvm:$microutilsVersion")
        implementation("ch.qos.logback:logback-classic:$logbackVersion")

        when (project.name) {
            "support" -> {}
            else -> {
                implementation(project(":support"))
            }
        }

        testImplementation("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
        testImplementation("io.mockk:mockk:$mockkVersion")
        testImplementation("org.assertj:assertj-core:$assertJVersion")
    }

    tasks.test {
        useJUnitPlatform()
    }


    val compileKotlin: KotlinCompile by tasks

    compileKotlin.kotlinOptions {
        jvmTarget = "17"
    }

    val compileTestKotlin: KotlinCompile by tasks

    compileTestKotlin.kotlinOptions {
        jvmTarget = "17"
    }
}
@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("multiplatform") version "1.9.20"
    //id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
}

kotlin {
    jvm()
    wasmJs {
        binaries.executable()
        browser {
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(project.rootDir.path)
                    }
                }

                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0-wasm2")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:2.3.7")
                implementation("io.ktor:ktor-server-netty:2.3.7")
                implementation("io.ktor:ktor-server-cors-jvm:2.3.7")
                implementation("io.ktor:ktor-server-content-negotiation:2.3.7")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
                implementation("ch.qos.logback:logback-classic:1.4.11")
            }
        }

        val wasmJsMain by getting {
            dependencies {
                implementation(npm("monaco-editor", "0.45.0"))
                implementation("io.ktor:ktor-client-core:3.0.0-wasm2")
                implementation("io.ktor:ktor-client-content-negotiation:3.0.0-wasm2")
                implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0-wasm2")
            }
        }
    }
}

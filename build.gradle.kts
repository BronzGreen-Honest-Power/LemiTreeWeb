import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest

val kotlinVersion = "1.9.0"
val serializationVersion = "1.3.3"
val ktorVersion = "2.2.2"
val logbackVersion = "1.2.11"

plugins {
    kotlin("multiplatform") version "1.9.0"
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.8.10"
    id("io.ktor.plugin") version "2.2.2"
}

group = "com.lemitree"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://maven.pkg.jetbrains.space/kotlin/p/compose/experimental")
}

application {
    mainClass.set("com.lemitree.server.ServerKt")
}

kotlin {
    js(IR) {
        browser {
            testTask(Action<KotlinJsTest> {
                testLogging.showStandardStreams = true
                useKarma {
                    useChromeHeadless()
                    useFirefox()
                }
            })
        }
        binaries.executable()
    }
    jvm {
        withJava()
    }
    ktor {
        fatJar {
            archiveFileName.set("lemitree.jar")
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.insert-koin:koin-core:3.3.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test-common:$kotlinVersion")
                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common:$kotlinVersion")
            }
        }
        val jsMain by getting {
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")

            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(compose.runtime)
                implementation(compose.html.core)
                implementation("io.ktor:ktor-client-js:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-client-json:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.runtime) // same module so it is required :/
                implementation("io.insert-koin:koin-core:3.3.3")
                implementation("io.insert-koin:koin-ktor:3.3.1")
                implementation("io.ktor:ktor-serialization:$ktorVersion")
                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-server-cors:$ktorVersion")
                implementation("io.ktor:ktor-server-compression:$ktorVersion")
                implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
            }
        }
    }
}

compose.experimental {
    web.application {}
}

afterEvaluate {
    rootProject.extensions.configure<NodeJsRootExtension> {
        nodeVersion = "16.0.0"
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.10.0"
    }
}

// include JS artifacts in any JAR we generate
//tasks.getByName<Jar>("jvmJar") {
//    val isProd = project.hasProperty("isProduction") || project.gradle.startParameter.taskNames.contains("installDist")
//    val taskName = if (isProd) "jsBrowserProductionWebpack" else "jsBrowserDevelopmentWebpack"
//    val webpackTask = tasks.getByName<KotlinWebpack>(taskName)
//    dependsOn(webpackTask) // make sure JS gets compiled first
//    from(File(webpackTask.destinationDirectory, webpackTask.outputFileName)) // bring output file along into the JAR
//}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

//distributions {
//    main {
//        contents {
//            from("$buildDir/libs") {
//                rename("${rootProject.name}-jvm", rootProject.name)
//                into("lib")
//            }
//        }
//    }
//}

// Alias "installDist" as "stage" (for cloud providers)
tasks.create("stage") {
    dependsOn(tasks.getByName("installDist"))
}
//
//tasks.getByName<JavaExec>("run") {
//    classpath(tasks.getByName<Jar>("jvmJar")) // so that the JS artifacts generated by `jvmJar` can be found and served
//}
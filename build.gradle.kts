import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("org.springframework.boot") version "4.0.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.20.0"
}

group = "cards.planes"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

tasks.register<GenerateTask>("generateServer") {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/src/main/resources/static/PlaneCards.yaml")
    outputDir.set("$buildDir/generated/server")
    packageName.set("cards.planes.generated")
    additionalProperties.set(
        mapOf(
            "requestMappingMode" to "none",
            "library" to "spring-declarative-http-interface"
        )
    )
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.1")
    implementation("tools.jackson.module:jackson-module-kotlin")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-websocket-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.jetbrains.exposed:exposed-core:1.1.1")
    implementation("org.jetbrains.exposed:exposed-dao:1.1.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:1.1.1")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:1.1.1")
    implementation("com.h2database:h2:2.4.240")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")

    implementation(project(":lufthansa-client"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
    sourceSets {
        main {
            kotlin.srcDir("${rootDir}/build/generated/server/src/main/kotlin")
        }
    }
}

tasks.compileJava.configure {
    dependsOn("generateServer")
}

tasks.compileKotlin.configure {
    dependsOn("generateServer")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openApiGenerate {
    generatorName.set("java")
    library.set("webclient")

    inputSpec.set("$projectDir/src/main/resources/lh-flight-schedules.json")
    outputDir.set("$rootDir/lufthansa-client")

    packageName.set("cards.planes.backend.generated.lufthansa")

    additionalProperties.set(
        mapOf(
            "dateLibrary" to "java8",
        )
    )
}

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    val kotlinVersion = "2.0.20"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.jmailen.kotlinter") version "4.4.1"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"

    id("com.epages.restdocs-api-spec") version "0.19.2"
    id("org.hidetake.swagger.generator") version "2.19.2"
}

group = "me.dgahn"
version = "1.0-SNAPSHOT"

val buildDir = layout.buildDirectory.get()
val snippetsDir by extra { file("$buildDir/generated-snippets") }
val restdocsDir by extra { file("$buildDir/resources/main/static/docs") }
tasks {
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("PASSED", "FAILED", "SKIPPED")
        }
        outputs.dir(snippetsDir)
        dependsOn(formatKotlin)
        finalizedBy("copyOasToBuildResource")
        finalizedBy("copyOasToSrcResource")
    }

    bootJar {
        dependsOn("copyOasToBuildResource")
    }

    register<Copy>("copyOasToBuildResource") {
        delete("src/main/resources/static/swagger-ui/openapi3.yaml")
        from("$buildDir/api-spec/openapi3.yaml")
        into("$buildDir/resources/main/static/swagger-ui/.")
        dependsOn("openapi3")
    }

    register<Copy>("copyOasToSrcResource") {
        delete("src/main/resources/static/swagger-ui/openapi3.yaml")
        from("$buildDir/api-spec/openapi3.yaml")
        into("src/main/resources/static/swagger-ui/.")
        dependsOn("openapi3")
    }

    named("resolveMainClassName") {
        mustRunAfter(":copyOasToBuildResource")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }

    compileKotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_22)
        }
    }
    compileTestKotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_22)
        }
    }
}

openapi3 {
    setServer("/")
    title = "패션 Documents"
    description = "패션 API 문서"
    version = "0.0.1"
    format = "yaml"
    delete {
        file("src/main/resources/static/swagger-ui/openapi3.yaml")
    }
    copy {
        from("$buildDir/api-spec/openapi3.yaml")
        into("src/main/resources/static/swagger-ui/.")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    /**
     * for kotlin
     */
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    /**
     * for spring
     */
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.data:spring-data-commons")

    /**
     * for database
     */
    implementation("com.h2database:h2")

    /**
     * for test
     */
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module = "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.kotest:kotest-assertions-core:5.8.1")

    /**
     * for restdocs
     */

    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.19.2")

    // for mock library
    testImplementation("io.mockk:mockk:1.13.9")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "src/generated/java")
        }
    }
}

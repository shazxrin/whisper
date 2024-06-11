import com.github.gradle.node.pnpm.task.PnpmTask

plugins {
    java

    id("com.github.ben-manes.versions") version "0.51.0"

    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"

    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
    id("com.github.node-gradle.node") version "7.0.2"
}

group = "me.shazxrin"
version = "1.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

node {
    nodeProjectDir.set(file("${projectDir}/client"))
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-amqp")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    implementation("org.telegram:telegrambots-springboot-longpolling-starter:7.4.0")
    implementation("org.telegram:telegrambots-client:7.4.0")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
}

tasks.register<Delete>("cleanClient") {
    delete(file("${projectDir}/build/resources/main/public"))
    delete(file("${projectDir}/build/resources/main/templates"))
    delete(file("${projectDir}/client/dist"))
}

tasks.register<PnpmTask>("buildClient") {
    dependsOn(tasks.named("cleanClient"))
    pnpmCommand.set(listOf("run", "build"))
}

tasks.register<Copy>("bundleClient") {
    dependsOn(tasks.named("buildClient"))
    from(file("${projectDir}/client/build/client"))
    into(file("${projectDir}/build/resources/main/public"))
}

tasks.register<PnpmTask>("generateClientApi") {
    dependsOn(tasks.named("generateOpenApiDocs"))

    pnpmCommand.set(listOf("run", "generateClientApi"))
}

tasks.forkedSpringBootRun.configure {
    dependsOn(tasks.named("pnpmSetup"))
    doNotTrackState("Workaround!")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

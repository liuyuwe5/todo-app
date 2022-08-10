import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


repositories {
    mavenCentral()
}

plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.asciidoctor.convert") version "1.5.8"
    id("org.liquibase.gradle") version "2.0.4"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"

}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("junit:junit:4.13.2")
    implementation("com.h2database:h2")
    implementation("mysql:mysql-connector-java:8.0.26")
    implementation("org.liquibase:liquibase-core:3.8.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

//tasks.withType<Test> {
//    useJUnitPlatform()
//}
//
//tasks.test {
//    project.property("snippetsDir")?.let { outputs.dir(it) }
//}
//
//tasks.asciidoctor {
//    project.property("snippetsDir")?.let { inputs.dir(it) }
//    dependsOn(tasks.test)
//}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.update {
    doLast {
        liquibase {
            activities.register("main") {
                this.arguments = mapOf(
                    "logLevel" to "info",
                    "changeLogFile" to "src/main/resources/db/changelog/db.changelog-master.yaml",
                    "url" to "jdbc:mysql://127.0.0.1:3306/todoapp",
                    "username" to "jade",
                    "password" to "jade",
                    "driver" to "com.mysql.cj.jdbc.Driver"
                )
            }
            runList = "main"
        }
    }
}


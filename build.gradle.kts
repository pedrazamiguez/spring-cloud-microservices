import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

repositories {
    mavenCentral()
}

plugins {
    id("org.springframework.boot") version("3.0.2") apply(false)
    id("io.spring.dependency-management") version("1.1.0")
    kotlin("jvm") version("1.7.22")
    kotlin("plugin.spring") version("1.7.22")
}

subprojects {

    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    group = "eu.pedrazamiguez.microservices"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_17

    repositories {
        mavenCentral()
        maven {
            url = uri("https://artifactory-oss.prod.netflix.net/artifactory/maven-oss-candidates")
        }
    }

    extra["springCloudVersion"] = "2022.0.1"
    extra["springContextIndexerVersion"] = "6.0.5"

    val implementation by configurations
    val developmentOnly by configurations
    val testImplementation by configurations

    dependencies {

        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        implementation("org.springframework.boot:spring-boot-starter-web") {
            exclude(module = "spring-boot-starter-tomcat")
        }
        implementation("org.springframework.boot:spring-boot-starter-undertow")

        implementation("org.springframework.cloud:spring-cloud-starter-config")
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-aop")
        implementation("io.github.resilience4j:resilience4j-spring-boot3")

        implementation("io.micrometer:micrometer-observation")
        implementation("io.micrometer:micrometer-tracing-bridge-brave")
        implementation("io.github.openfeign:feign-micrometer")
        implementation("io.zipkin.reporter2:zipkin-reporter-brave")
        implementation("org.springframework.amqp:spring-rabbit")

        annotationProcessor("org.springframework:spring-context-indexer:${property("springContextIndexerVersion")}")

        developmentOnly("org.springframework.boot:spring-boot-devtools")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.micrometer:micrometer-observation-test")

    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<BootBuildImage> {
        imageName.set("apedraza/${project.name}:latest")
        publish.set(true)
        docker {
            publishRegistry {
                username.set("apedraza")
                password.set("dckr_pat_2bEUK9eTdaSzpxNNqVXyGgrKXyc")
            }
        }
    }

}

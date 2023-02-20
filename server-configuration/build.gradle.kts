dependencies {
    implementation("org.springframework.cloud:spring-cloud-config-server") {
        exclude(module = "spring-boot-starter-tomcat")
    }
}

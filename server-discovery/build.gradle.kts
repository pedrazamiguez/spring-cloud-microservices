dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server") {
        exclude(module = "spring-boot-starter-tomcat")
    }
}

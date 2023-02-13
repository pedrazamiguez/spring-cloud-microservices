package eu.pedrazamiguez.microservices.service.authentication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServiceAuthenticationApplication

fun main(args: Array<String>) {
	runApplication<ServiceAuthenticationApplication>(*args)
}

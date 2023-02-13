package eu.pedrazamiguez.microservices.service.search

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServiceSearchApplication

fun main(args: Array<String>) {
	runApplication<ServiceSearchApplication>(*args)
}

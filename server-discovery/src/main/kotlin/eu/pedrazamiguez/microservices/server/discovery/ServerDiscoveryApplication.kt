package eu.pedrazamiguez.microservices.server.discovery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServerDiscoveryApplication

fun main(args: Array<String>) {
	runApplication<ServerDiscoveryApplication>(*args)
}

package eu.pedrazamiguez.microservices.server.discovery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class ServerDiscoveryApplication

fun main(args: Array<String>) {
	runApplication<ServerDiscoveryApplication>(*args)
}

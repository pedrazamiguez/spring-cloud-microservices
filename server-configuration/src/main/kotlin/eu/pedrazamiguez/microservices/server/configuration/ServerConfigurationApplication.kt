package eu.pedrazamiguez.microservices.server.configuration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class ServerConfigurationApplication

fun main(args: Array<String>) {
    runApplication<ServerConfigurationApplication>(*args)
}

package eu.pedrazamiguez.microservices.service.folder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class ServiceFolderApplication

fun main(args: Array<String>) {
	runApplication<ServiceFolderApplication>(*args)
}

package eu.pedrazamiguez.microservices.service.folder.proxy

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "service-file", url = "localhost:32000")
interface ServiceFileProxy {

    @GetMapping("/types")
    fun getFileTypes(): Map<String, Any>

    @GetMapping("/types/{type}")
    fun findFileType(@PathVariable type: String): Map<String, Any>

}

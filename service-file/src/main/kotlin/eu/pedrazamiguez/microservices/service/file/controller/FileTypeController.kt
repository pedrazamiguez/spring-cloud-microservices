package eu.pedrazamiguez.microservices.service.file.controller

import io.github.resilience4j.bulkhead.annotation.Bulkhead
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/types")
class FileTypeController {

    @Value("\${service-file.types:}")
    private lateinit var fileTypes: String

    @Value("\${service-file.maxFileSize:}")
    private lateinit var maxFileSize: String

    @Value("\${service-file.allowUpload:}")
    private lateinit var allowUpload: String

    @Value("\${app.environment.displayName:}")
    private lateinit var environmentDisplayName: String

    @Value("\${app.db.uri:}")
    private lateinit var dbUri: String

    @Autowired
    private lateinit var webServerAppContext: ServletWebServerApplicationContext

    @GetMapping
    @Retry(name = "lazy", fallbackMethod = "getFileTypesFallback")
    //@CircuitBreaker(name = "default", fallbackMethod = "getFileTypesFallback")
    @RateLimiter(name = "test")
    @Bulkhead(name = "default")
    fun getFileTypes(): Map<String, Any> {
        RestTemplate().exchange("http://dummy.xxx", HttpMethod.GET, null, String::class.java)
        return mapOf(
            "types" to fileTypes.split(","),
            "maxFileSize" to maxFileSize,
            "allowUpload" to allowUpload.toBoolean(),
            "env" to environmentDisplayName,
            "dbUri" to dbUri,
            "port" to webServerAppContext.webServer.port
        )
    }

    fun getFileTypesFallback(e: Exception): Map<String, Any> = mapOf(
        "message" to "getFileTypes endpoint call failed",
        "error" to "${e.message}"
    )

    @GetMapping("/{type}")
    fun findFileType(@PathVariable type: String): Map<String, Any> {
        val fileTypesList: List<String> = fileTypes.split(",").map { it.trim() }
        return mapOf(
            "type" to type,
            "found" to fileTypesList.contains(type)
        )
    }
}

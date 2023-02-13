package eu.pedrazamiguez.microservices.service.file.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/types")
class FileTypeController {

    @Value("\${service-file.types:}")
    lateinit var fileTypes: String

    @Value("\${service-file.maxFileSize:}")
    lateinit var maxFileSize: String

    @Value("\${service-file.allowUpload:}")
    lateinit var allowUpload: String

    @Value("\${app.environment.displayName:}")
    lateinit var environmentDisplayName: String

    @Value("\${app.db.uri:}")
    lateinit var dbUri: String

    @GetMapping
    fun getFileTypes(): Map<String, Any> = mapOf(
        "types" to fileTypes.split(","),
        "maxFileSize" to maxFileSize,
        "allowUpload" to allowUpload.toBoolean(),
        "env" to environmentDisplayName,
        "dbUri" to dbUri
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

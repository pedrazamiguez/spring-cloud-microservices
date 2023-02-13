package eu.pedrazamiguez.microservices.service.folder.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/folders")
class ListingController {

    @GetMapping("/{folderId}")
    fun getFolderListing(@PathVariable folderId: String) = mapOf(
        "folderId" to folderId,
        "allowedFileTypes" to listOf<String>()
    )

}

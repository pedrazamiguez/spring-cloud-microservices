package eu.pedrazamiguez.microservices.service.folder.controller

import eu.pedrazamiguez.microservices.service.folder.proxy.ServiceFileProxy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/folders")
class ListingController {

    @Autowired
    private lateinit var serviceFileProxy: ServiceFileProxy

    @GetMapping("/{folderId}")
    fun getFolderListing(@PathVariable folderId: String) = mapOf(
        "folderId" to folderId,
        "allowedFileTypes" to serviceFileProxy.getFileTypes(),
        "txt" to serviceFileProxy.findFileType("txt")
    )

}

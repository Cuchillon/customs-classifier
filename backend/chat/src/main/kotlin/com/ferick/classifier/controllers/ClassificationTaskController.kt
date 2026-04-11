package com.ferick.classifier.controllers

import com.ferick.classifier.model.dto.ClassificationTaskCreateRequest
import com.ferick.classifier.model.dto.ClassificationTaskMeta
import com.ferick.classifier.service.ClassificationTaskService
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class ClassificationTaskController(
    private val classificationTaskService: ClassificationTaskService
) {

    @PostMapping("/classify", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @RequestPart meta: ClassificationTaskMeta,
        @RequestPart data: MultipartFile
    ) = classificationTaskService.create(
        ClassificationTaskCreateRequest(meta, data.bytes)
    )

    @GetMapping("/classify")
    fun getAll() = classificationTaskService.getAll()

    @GetMapping("/classify/{id}/status")
    fun getStatus(@PathVariable("id") id: Long) = classificationTaskService.getStatus(id)

    @GetMapping("/classify/{id}/download")
    fun download(@PathVariable("id") id: Long): ResponseEntity<Resource> {
        val bytes = classificationTaskService.download(id)
        val fileName = "$id-result-file.xlsx"
        return ResponseEntity
            .ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                """attachment; filename="$fileName"""")
            .body(ByteArrayResource(bytes))
    }
}

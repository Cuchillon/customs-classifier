package com.ferick.classifier.controllers

import com.ferick.classifier.model.dto.ClassificationTaskCreateRequest
import com.ferick.classifier.model.dto.ClassificationTaskMeta
import com.ferick.classifier.service.ClassificationTaskService
import org.springframework.http.MediaType
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
}

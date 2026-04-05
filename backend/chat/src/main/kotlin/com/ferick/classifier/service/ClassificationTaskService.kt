package com.ferick.classifier.service

import com.ferick.classifier.model.dto.ClassificationTaskCreateRequest
import com.ferick.classifier.model.dto.ClassificationTaskCreateResponse
import com.ferick.classifier.model.dto.ClassificationTaskStatusResponse

interface ClassificationTaskService {
    fun create(request: ClassificationTaskCreateRequest): ClassificationTaskCreateResponse
    fun getStatus(id: Long): ClassificationTaskStatusResponse
    fun download(id: Long): ByteArray
}

package com.ferick.classifier.service

import com.ferick.classifier.model.dto.ClassificationTaskCreateRequest
import com.ferick.classifier.model.dto.ClassificationTaskCreateResponse
import com.ferick.classifier.model.dto.ClassificationTaskStatusResponse
import com.ferick.classifier.model.dto.ClassificationTasksResponse

interface ClassificationTaskService {
    fun create(request: ClassificationTaskCreateRequest): ClassificationTaskCreateResponse
    fun getAll(): ClassificationTasksResponse
    fun getStatus(id: Long): ClassificationTaskStatusResponse
    fun download(id: Long): ByteArray
}

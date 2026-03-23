package com.ferick.classifier.service

import com.ferick.classifier.model.dto.ClassificationTaskCreateRequest
import com.ferick.classifier.model.dto.ClassificationTaskCreateResponse

interface ClassificationTaskService {
    fun create(request: ClassificationTaskCreateRequest): ClassificationTaskCreateResponse
}

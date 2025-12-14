package com.ferick.classifier.service

import com.ferick.classifier.model.dto.ValidateRequest
import com.ferick.classifier.model.dto.ValidateResponse

interface ApiKeyService {
    fun validateApiKey(request: ValidateRequest): ValidateResponse
}

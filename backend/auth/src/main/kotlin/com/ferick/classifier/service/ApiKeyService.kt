package com.ferick.classifier.service

import com.ferick.classifier.model.dto.ApiKeyRequest
import com.ferick.classifier.model.dto.ApiKeyResponse
import com.ferick.classifier.model.dto.ValidateRequest
import com.ferick.classifier.model.dto.ValidateResponse

interface ApiKeyService {
    fun generateApiKey(username: String, request: ApiKeyRequest): ApiKeyResponse
    fun validateApiKey(request: ValidateRequest): ValidateResponse
}

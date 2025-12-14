package com.ferick.classifier.service.impl

import com.ferick.classifier.model.dto.ValidKeyData
import com.ferick.classifier.model.dto.ValidateRequest
import com.ferick.classifier.model.dto.ValidateResponse
import com.ferick.classifier.service.ApiKeyService
import java.time.Instant

class ApiKeyServiceImpl : ApiKeyService {

    override fun validateApiKey(request: ValidateRequest): ValidateResponse {
        return if (request.apiKey == "api-key-1") {
            ValidateResponse(
                valid = true,
                data = ValidKeyData(
                    username = "user",
                    expiresAt = Instant.now().plusSeconds(3600),
                    scopes = setOf("store:all")
                )
            )
        } else {
            ValidateResponse(false)
        }
    }
}
package com.ferick.classifier.controllers

import com.ferick.classifier.model.dto.ApiKeyRequest
import com.ferick.classifier.model.dto.ApiKeyResponse
import com.ferick.classifier.model.dto.ValidateRequest
import com.ferick.classifier.model.dto.ValidateResponse
import com.ferick.classifier.service.ApiKeyService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/api-key")
class ApiKeyController(
    private val apiKeyService: ApiKeyService
) {

    @PostMapping("/generate")
    fun createApiKey(
        @RequestBody request: ApiKeyRequest,
        @AuthenticationPrincipal user: User
    ): ApiKeyResponse = apiKeyService.generateApiKey(user.username, request)

    @PostMapping("/validate")
    fun validateApiKey(
        @RequestBody request: ValidateRequest
    ): ValidateResponse = apiKeyService.validateApiKey(request)
}

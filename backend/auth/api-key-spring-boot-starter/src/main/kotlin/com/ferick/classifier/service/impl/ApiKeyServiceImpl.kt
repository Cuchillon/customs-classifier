package com.ferick.classifier.service.impl

import com.ferick.classifier.exceptions.ApiKeyProviderException
import com.ferick.classifier.model.dto.ValidateRequest
import com.ferick.classifier.model.dto.ValidateResponse
import com.ferick.classifier.service.ApiKeyService
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.client.RestClient

class ApiKeyServiceImpl(
    private val client: RestClient
) : ApiKeyService {

    @Cacheable(value = ["apiKeys"], keyGenerator = "apiKeyCachingKeyGenerator")
    override fun validateApiKey(request: ValidateRequest): ValidateResponse {
        return client.post()
            .body(request)
            .retrieve()
            .onStatus({ statusCode -> statusCode.is4xxClientError || statusCode.is5xxServerError }) { _, res ->
                throw ApiKeyProviderException(
                    "Getting API key failed with ${res.statusCode.value()}, ${res.statusText}"
                )
            }
            .body(ValidateResponse::class.java)
            ?: throw ApiKeyProviderException("Getting API key returned empty body")
    }
}
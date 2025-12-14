package com.ferick.classifier.service.impl

import com.ferick.classifier.common.extensions.toResponse
import com.ferick.classifier.model.dto.ApiKeyRequest
import com.ferick.classifier.model.dto.ApiKeyResponse
import com.ferick.classifier.model.dto.ValidKeyData
import com.ferick.classifier.model.dto.ValidateRequest
import com.ferick.classifier.model.dto.ValidateResponse
import com.ferick.classifier.model.entities.ApiKey
import com.ferick.classifier.repository.ApiKeyRepository
import com.ferick.classifier.service.ApiKeyService
import com.ferick.classifier.service.KeyEncodingService
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.UUID

@Service
class ApiKeyServiceImpl(
    private val apiKeyRepository: ApiKeyRepository,
    private val keyEncodingService: KeyEncodingService,
    private val userDetailsManager: UserDetailsManager
) : ApiKeyService {

    override fun generateApiKey(username: String, request: ApiKeyRequest): ApiKeyResponse {
        if (!userDetailsManager.userExists(username)) {
            throw IllegalArgumentException("User not found: $username")
        }

        val keyId = UUID.randomUUID()
        val rawKey = keyEncodingService.generateRawKey(keyId)
        val keyHash = keyEncodingService.hashKey(rawKey)
        val expiresAt = Instant.now().plus(Duration.ofSeconds(request.ttlSeconds ?: DEFAULT_TTL_SECONDS))

        val apiKey = ApiKey(
            keyId = keyId,
            keyHash = keyHash,
            username = username,
            expiresAt = expiresAt,
            scopes = request.scopes.toMutableSet()
        )

        return apiKeyRepository.save(apiKey).toResponse(rawKey)
    }

    override fun validateApiKey(request: ValidateRequest): ValidateResponse {
        val parts = request.apiKey.split("_", limit = 3)
        if (parts.size != 3 || parts[0] != "sk") {
            return invalidApiKeyResponse
        }

        val keyId = try {
            UUID.fromString(parts[1])
        } catch (e: IllegalArgumentException) {
            return invalidApiKeyResponse
        }

        val apiKey = apiKeyRepository.findByKeyId(keyId) ?: return invalidApiKeyResponse

        return if (
            apiKey.active.not()
            || (apiKey.expiresAt.isBefore(Instant.now()))
            || !keyEncodingService.matches(request.apiKey, apiKey.keyHash)
        ) {
            invalidApiKeyResponse
        } else {
            ValidateResponse(
                valid = true,
                data = ValidKeyData(
                    username = apiKey.username,
                    expiresAt = apiKey.expiresAt,
                    scopes = apiKey.scopes
                )
            )
        }
    }

    companion object {
        private const val DEFAULT_TTL_SECONDS = 86400L
        private val invalidApiKeyResponse = ValidateResponse(valid = false)
    }
}
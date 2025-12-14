package com.ferick.classifier.common.extensions

import com.ferick.classifier.model.dto.ApiKeyResponse
import com.ferick.classifier.model.entities.ApiKey

fun ApiKey.toResponse(rawKey: String): ApiKeyResponse = ApiKeyResponse(
    id = this.id!!,
    keyId = this.keyId,
    scopes = this.scopes,
    createdAt = this.createdAt,
    expiresAt = this.expiresAt,
    active = this.active,
    rawKey = rawKey
)

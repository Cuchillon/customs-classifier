package com.ferick.classifier.model.dto

import java.time.Instant
import java.util.UUID

data class ApiKeyResponse(
    val id: Long,
    val keyId: UUID,
    val scopes: Set<String>,
    val createdAt: Instant,
    val expiresAt: Instant?,
    val active: Boolean,
    val rawKey: String
)

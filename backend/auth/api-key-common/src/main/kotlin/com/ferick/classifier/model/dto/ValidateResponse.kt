package com.ferick.classifier.model.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ValidateResponse(
    val valid: Boolean,
    val data: ValidKeyData? = null
)

data class ValidKeyData(
    val username: String,
    val expiresAt: Instant,
    val scopes: Set<String> = emptySet()
)

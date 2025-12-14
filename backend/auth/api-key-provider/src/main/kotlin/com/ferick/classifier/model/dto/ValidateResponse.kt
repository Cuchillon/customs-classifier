package com.ferick.classifier.model.dto

import java.time.Instant

data class ValidateResponse(
    val valid: Boolean,
    val expiresAt: Instant? = null
)

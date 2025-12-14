package com.ferick.classifier.model.dto

data class ApiKeyRequest(
    val scopes: Set<String>,
    val ttlSeconds: Long? = null
)

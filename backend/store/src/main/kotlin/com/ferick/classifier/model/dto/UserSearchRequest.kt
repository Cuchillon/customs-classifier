package com.ferick.classifier.model.dto

data class UserSearchRequest(
    val query: String,
    val topK: Int,
    val similarityThreshold: Double,
    val meta: UserSearchRequestMeta? = null
)

data class UserSearchRequestMeta(
    val clients: List<String>,
    val specifications: List<String>
)

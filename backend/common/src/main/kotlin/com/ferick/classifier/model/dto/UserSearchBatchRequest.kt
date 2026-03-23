package com.ferick.classifier.model.dto

data class UserSearchBatchRequest(
    val queries: List<String>,
    val topK: Int,
    val similarityThreshold: Double,
    val meta: UserSearchRequestMeta? = null
)

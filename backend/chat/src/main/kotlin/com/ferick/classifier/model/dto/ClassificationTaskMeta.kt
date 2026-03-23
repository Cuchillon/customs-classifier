package com.ferick.classifier.model.dto

data class ClassificationTaskMeta(
    val topK: Int,
    val similarityThreshold: Double,
    val meta: UserSearchRequestMeta? = null
)

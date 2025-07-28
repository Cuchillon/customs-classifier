package com.ferick.classifier.model.dto

data class UserSearchResponse(
    val items: List<UserSearchResponseItem>
)

data class UserSearchResponseItem(
    val text: String,
    val score: Double,
    val meta: StoreMeta
)

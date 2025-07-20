package com.ferick.classifier.model.dto

data class StoreRequest(
    val meta: StoreRequestMeta
)

data class StoreRequestMeta(
    val client: String,
    val specification: String
)

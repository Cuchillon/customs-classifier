package com.ferick.classifier.model.dto

data class JsonDocument(
    val items: List<JsonDocumentItem>
)

data class JsonDocumentItem(
    val code: String,
    val text: String
)

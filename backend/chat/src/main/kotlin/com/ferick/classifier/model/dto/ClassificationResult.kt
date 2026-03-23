package com.ferick.classifier.model.dto

data class ClassificationResult(
    val items: List<ClassificationResultItem>
)

data class ClassificationResultItem(
    val code: String,
    val text: String
)

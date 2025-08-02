package com.ferick.classifier.model.documents

data class Specification(
    val items: List<SpecificationItem>
)

data class SpecificationItem(
    val code: String,
    val text: String
)

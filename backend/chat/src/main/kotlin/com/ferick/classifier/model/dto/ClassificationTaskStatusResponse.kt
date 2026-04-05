package com.ferick.classifier.model.dto

import com.ferick.classifier.model.entity.ClassificationTaskStatus

data class ClassificationTaskStatusResponse(
    val id: Long,
    val status: ClassificationTaskStatus
)

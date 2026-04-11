package com.ferick.classifier.model.dto

import com.ferick.classifier.model.entity.ClassificationTaskStatus
import java.time.LocalDateTime

data class ClassificationTasksResponse(
    val items: List<ClassificationTaskItem>
)

data class ClassificationTaskItem(
    val id: Long,
    val createdAt: LocalDateTime,
    val status: ClassificationTaskStatus,
    val meta: ClassificationTaskMeta,
    var storageFileId: String? = null
)

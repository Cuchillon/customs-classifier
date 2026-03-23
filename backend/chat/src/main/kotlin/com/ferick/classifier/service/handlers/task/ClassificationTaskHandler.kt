package com.ferick.classifier.service.handlers.task

import com.ferick.classifier.model.entity.ClassificationTask
import com.ferick.classifier.model.entity.ClassificationTaskStatus

interface ClassificationTaskHandler {
    val startStatus: ClassificationTaskStatus
    val endStatus: ClassificationTaskStatus

    fun supports(status: ClassificationTaskStatus): Boolean = status == startStatus

    fun process(task: ClassificationTask)
}

package com.ferick.classifier.service.handlers.subtask

import com.ferick.classifier.model.entity.ClassificationSubtask
import com.ferick.classifier.model.entity.ClassificationSubtaskStatus

interface ClassificationSubtaskHandler {
    val startStatus: ClassificationSubtaskStatus
    val endStatus: ClassificationSubtaskStatus

    fun supports(status: ClassificationSubtaskStatus): Boolean = status == startStatus

    fun process(subtask: ClassificationSubtask)
}

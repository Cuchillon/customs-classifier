package com.ferick.classifier.common.extensions

import com.ferick.classifier.model.dto.ClassificationResult
import com.ferick.classifier.model.dto.ClassificationResultItem
import com.ferick.classifier.model.entity.ClassificationSubtask

fun List<ClassificationSubtask>.toResult(): ClassificationResult =
    this.flatMap { subtask ->
        subtask.data.map { ClassificationResultItem(code = it.code ?: "empty", text = it.text) }
    }.let { items ->
        ClassificationResult(items)
    }
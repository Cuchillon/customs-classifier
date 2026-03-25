package com.ferick.classifier.repository

import com.ferick.classifier.model.entity.ClassificationSubtask
import com.ferick.classifier.model.entity.ClassificationSubtaskStatus
import org.springframework.data.jpa.repository.JpaRepository

interface ClassificationSubtaskRepository : JpaRepository<ClassificationSubtask, Long> {
    fun findByClassificationTaskId(classificationTaskId: Long): List<ClassificationSubtask>
    fun findFirstByClassificationTaskIdAndStatusIn(
        classificationTaskId: Long,
        statuses: Set<ClassificationSubtaskStatus>
    ): ClassificationSubtask?
    fun countByClassificationTaskId(classificationTaskId: Long): Long
    fun countByClassificationTaskIdAndStatusIn(
        classificationTaskId: Long,
        statuses: Set<ClassificationSubtaskStatus>
    ): Long
}

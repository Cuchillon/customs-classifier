package com.ferick.classifier.repository

import com.ferick.classifier.model.entity.ClassificationTask
import com.ferick.classifier.model.entity.ClassificationTaskStatus
import org.springframework.data.jpa.repository.JpaRepository

interface ClassificationTaskRepository : JpaRepository<ClassificationTask, Long> {
    fun findFirstByStatus(status: ClassificationTaskStatus): ClassificationTask?
    fun findFirst5ByStatusIn(statuses: Set<ClassificationTaskStatus>): List<ClassificationTask>
}

package com.ferick.classifier.repository

import com.ferick.classifier.model.entity.ClassificationSubtask
import org.springframework.data.jpa.repository.JpaRepository

interface ClassificationSubtaskRepository : JpaRepository<ClassificationSubtask, Long>

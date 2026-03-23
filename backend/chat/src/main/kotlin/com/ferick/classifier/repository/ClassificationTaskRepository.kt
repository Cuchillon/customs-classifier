package com.ferick.classifier.repository

import com.ferick.classifier.model.entity.ClassificationTask
import org.springframework.data.jpa.repository.JpaRepository

interface ClassificationTaskRepository : JpaRepository<ClassificationTask, Long>

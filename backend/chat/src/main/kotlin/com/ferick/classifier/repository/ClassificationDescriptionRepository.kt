package com.ferick.classifier.repository

import com.ferick.classifier.model.entity.ClassificationData
import org.springframework.data.jpa.repository.JpaRepository

interface ClassificationDescriptionRepository : JpaRepository<ClassificationData, Long>

package com.ferick.classifier.repository

import com.ferick.classifier.model.entity.ClassificationDescription
import org.springframework.data.jpa.repository.JpaRepository

interface ClassificationDescriptionRepository : JpaRepository<ClassificationDescription, Long>

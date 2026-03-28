package com.ferick.classifier.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "classification_data")
class ClassificationData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val text: String,
    var code: String? = null,

    @ManyToOne(targetEntity = ClassificationSubtask::class)
    @JoinColumn(name = "classification_subtask_id", referencedColumnName = "id")
    val classificationSubtask: ClassificationSubtask
)

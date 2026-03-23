package com.ferick.classifier.model.entity

import com.ferick.classifier.model.dto.ClassificationTaskMeta
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "classification_tasks")
class ClassificationTask(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    var status: ClassificationTaskStatus = ClassificationTaskStatus.STARTED,

    @JdbcTypeCode(SqlTypes.JSON)
    val meta: ClassificationTaskMeta
)

enum class ClassificationTaskStatus {
    STARTED, COMPLETED, DONE, PRINT_ERROR
}

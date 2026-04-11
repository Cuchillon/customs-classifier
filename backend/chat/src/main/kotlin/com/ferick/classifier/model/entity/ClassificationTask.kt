package com.ferick.classifier.model.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.ferick.classifier.model.dto.ClassificationTaskMeta
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "classification_tasks")
@EntityListeners(AuditingEntityListener::class)
class ClassificationTask(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var createdAt: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    var status: ClassificationTaskStatus = ClassificationTaskStatus.STARTED,

    @JdbcTypeCode(SqlTypes.JSON)
    val meta: ClassificationTaskMeta,

    var storageFileId: String? = null
)

enum class ClassificationTaskStatus {
    STARTED, COMPLETED, DONE, ERROR
}

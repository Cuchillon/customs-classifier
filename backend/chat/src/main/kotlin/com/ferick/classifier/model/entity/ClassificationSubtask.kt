package com.ferick.classifier.model.entity

import com.ferick.classifier.model.dto.UserSearchResponse
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "classification_subtasks")
class ClassificationSubtask(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    var status: ClassificationSubtaskStatus = ClassificationSubtaskStatus.STARTED,

    @ManyToOne(targetEntity = ClassificationTask::class)
    @JoinColumn(name = "classification_task_id", referencedColumnName = "id")
    val classificationTask: ClassificationTask,

    @OneToMany(
        mappedBy = "classification_subtask",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val descriptions: List<ClassificationDescription>,

    @JdbcTypeCode(SqlTypes.JSON)
    var context: UserSearchResponse? = null
)

enum class ClassificationSubtaskStatus {
    STARTED, CONTEXT_LOADED, CHAT_CALLED, DONE, ERROR
}

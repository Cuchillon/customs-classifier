package com.ferick.classifier.service.handlers.task

import com.ferick.classifier.model.entity.ClassificationSubtaskStatus.CHAT_CALLED
import com.ferick.classifier.model.entity.ClassificationSubtaskStatus.ERROR
import com.ferick.classifier.model.entity.ClassificationTask
import com.ferick.classifier.model.entity.ClassificationTaskStatus
import com.ferick.classifier.repository.ClassificationSubtaskRepository
import com.ferick.classifier.repository.ClassificationTaskRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CompletedHandler(
    private val classificationTaskRepository: ClassificationTaskRepository,
    private val classificationSubtaskRepository: ClassificationSubtaskRepository
) : ClassificationTaskHandler {
    override val startStatus = ClassificationTaskStatus.STARTED
    override val endStatus = ClassificationTaskStatus.COMPLETED

    @Transactional
    override fun process(task: ClassificationTask) {
        task.id?.also {
            val allSubtaskCount = classificationSubtaskRepository.countByClassificationTaskId(it)
            val completedSubtaskCount = classificationSubtaskRepository
                .countByClassificationTaskIdAndStatusIn(it, statuses)
            if (completedSubtaskCount < allSubtaskCount) {
                task.status = ClassificationTaskStatus.STARTED
            } else {
                task.status = ClassificationTaskStatus.COMPLETED
            }
            classificationTaskRepository.save(task)
        }
    }

    companion object {
        private val statuses = setOf(CHAT_CALLED, ERROR)
    }
}
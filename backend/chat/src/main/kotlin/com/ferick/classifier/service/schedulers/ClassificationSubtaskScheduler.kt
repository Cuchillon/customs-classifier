package com.ferick.classifier.service.schedulers

import com.ferick.classifier.model.entity.ClassificationSubtaskStatus.CONTEXT_LOADED
import com.ferick.classifier.model.entity.ClassificationSubtaskStatus.STARTED
import com.ferick.classifier.model.entity.ClassificationTaskStatus
import com.ferick.classifier.repository.ClassificationSubtaskRepository
import com.ferick.classifier.repository.ClassificationTaskRepository
import com.ferick.classifier.service.handlers.subtask.ClassificationSubtaskHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ClassificationSubtaskScheduler(
    private val schedulerDispatcher: CoroutineDispatcher,
    private val classificationTaskRepository: ClassificationTaskRepository,
    private val classificationSubtaskRepository: ClassificationSubtaskRepository,
    private val handlers: Set<ClassificationSubtaskHandler>
) {

    @Scheduled(cron = "*/10 * * * * *")
    fun process() = runBlocking(schedulerDispatcher) {
        classificationTaskRepository.findFirstByStatus(ClassificationTaskStatus.STARTED)?.let { task ->
            classificationSubtaskRepository.findFirstByClassificationTaskIdAndStatusIn(
                task.id!!, statuses
            )?.let { subtask ->
                handlers.find { it.supports(subtask.status) }?.process(subtask)
                    ?: throw IllegalStateException(
                        "There is no handler for classification subtask status ${subtask.status.name}"
                    )
            }
        }
    }

    companion object {
        private val statuses = setOf(STARTED, CONTEXT_LOADED)
    }
}

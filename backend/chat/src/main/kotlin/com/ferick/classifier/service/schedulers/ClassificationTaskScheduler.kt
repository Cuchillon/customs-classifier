package com.ferick.classifier.service.schedulers

import com.ferick.classifier.model.entity.ClassificationTaskStatus.COMPLETED
import com.ferick.classifier.model.entity.ClassificationTaskStatus.STARTED
import com.ferick.classifier.repository.ClassificationTaskRepository
import com.ferick.classifier.service.handlers.task.ClassificationTaskHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ClassificationTaskScheduler(
    private val schedulerDispatcher: CoroutineDispatcher,
    private val classificationTaskRepository: ClassificationTaskRepository,
    private val handlers: Set<ClassificationTaskHandler>
) {

    @Scheduled(cron = "*/30 * * * * *")
    fun process() = runBlocking(schedulerDispatcher) {
        classificationTaskRepository.findFirst5ByStatusIn(statuses).forEach { task ->
            handlers.find { it.supports(task.status) }?.process(task)
                ?: throw IllegalStateException(
                    "There is no handler for classification task status ${task.status.name}"
                )
        }
    }

    companion object {
        private val statuses = setOf(STARTED, COMPLETED)
    }
}

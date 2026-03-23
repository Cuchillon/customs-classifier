package com.ferick.classifier.service.handlers.subtask

import com.ferick.classifier.model.entity.ClassificationSubtask
import com.ferick.classifier.model.entity.ClassificationSubtaskStatus
import com.ferick.classifier.repository.ClassificationSubtaskRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ChatCallHandler(
    private val classificationSubtaskRepository: ClassificationSubtaskRepository
) : ClassificationSubtaskHandler {
    override val startStatus = ClassificationSubtaskStatus.CONTEXT_LOADED
    override val endStatus = ClassificationSubtaskStatus.CHAT_CALLED

    @Transactional
    override fun process(subtask: ClassificationSubtask) {
        subtask.status = endStatus
        classificationSubtaskRepository.save(subtask)
    }
}

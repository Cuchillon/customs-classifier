package com.ferick.classifier.service.handlers.subtask

import com.ferick.classifier.model.dto.UserSearchBatchRequest
import com.ferick.classifier.model.entity.ClassificationSubtask
import com.ferick.classifier.model.entity.ClassificationSubtaskStatus
import com.ferick.classifier.repository.ClassificationSubtaskRepository
import com.ferick.classifier.service.clients.StoreClient
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ContextLoadHandler(
    private val classificationSubtaskRepository: ClassificationSubtaskRepository,
    private val storeClient: StoreClient
) : ClassificationSubtaskHandler {
    override val startStatus = ClassificationSubtaskStatus.STARTED
    override val endStatus = ClassificationSubtaskStatus.CONTEXT_LOADED

    @Transactional
    override fun process(subtask: ClassificationSubtask) {
        val request = UserSearchBatchRequest(
            queries = subtask.data.map { it.text },
            topK = subtask.classificationTask.meta.topK,
            similarityThreshold = subtask.classificationTask.meta.similarityThreshold,
            meta = subtask.classificationTask.meta.meta
        )
        try {
            subtask.context = storeClient.loadContext(request)
            subtask.status = endStatus
        } catch (e: Exception) {
            subtask.status = ClassificationSubtaskStatus.ERROR
        }
        classificationSubtaskRepository.save(subtask)
    }
}

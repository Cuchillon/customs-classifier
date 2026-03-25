package com.ferick.classifier.service.handlers.subtask

import com.ferick.classifier.common.extensions.toContext
import com.ferick.classifier.common.extensions.toQuery
import com.ferick.classifier.model.entity.ClassificationSubtask
import com.ferick.classifier.model.entity.ClassificationSubtaskStatus
import com.ferick.classifier.repository.ClassificationSubtaskRepository
import com.ferick.classifier.service.agents.states.ClassificationAgentState
import org.bsc.langgraph4j.CompiledGraph
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ChatCallHandler(
    private val classificationSubtaskRepository: ClassificationSubtaskRepository,
    private val graph: CompiledGraph<ClassificationAgentState>
) : ClassificationSubtaskHandler {
    override val startStatus = ClassificationSubtaskStatus.CONTEXT_LOADED
    override val endStatus = ClassificationSubtaskStatus.CHAT_CALLED

    @Transactional
    override fun process(subtask: ClassificationSubtask) {
        val initData = mapOf(
            ClassificationAgentState.INPUT to subtask.toQuery(),
            ClassificationAgentState.CONTEXT to subtask.context!!.toContext()
        )

        try {
            val result = graph.invoke(initData)
                .map { it.output() }
                .orElseThrow { IllegalStateException("Failed to get classification result") }
            subtask.data.forEach { dataItem ->
                dataItem.code = result.answer.find { it.id == dataItem.id }?.code ?: "not found"
            }
            subtask.status = endStatus
        } catch (e: Exception) {
            subtask.status = ClassificationSubtaskStatus.ERROR
        }
        classificationSubtaskRepository.save(subtask)
    }
}

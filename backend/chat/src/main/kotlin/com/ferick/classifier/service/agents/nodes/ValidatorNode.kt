package com.ferick.classifier.service.agents.nodes

import com.fasterxml.jackson.databind.ObjectMapper
import com.ferick.classifier.model.dto.AgentResult
import com.ferick.classifier.service.agents.states.ClassificationAgentState
import org.bsc.langgraph4j.action.NodeAction
import org.springframework.stereotype.Component

@Component
class ValidatorNode(
    private val objectMapper: ObjectMapper
) : NodeAction<ClassificationAgentState> {

    override fun apply(state: ClassificationAgentState): MutableMap<String, Any> {
        val classificationResult = state.classificationResult()
        val validationCount = state.validationCount()
        val validationResult = validate(classificationResult)

        return if (validationResult.isSuccess) {
            mutableMapOf(
                ClassificationAgentState.NEXT_AGENT to ClassificationAgentState.END,
                ClassificationAgentState.OUTPUT to validationResult.result!!,
                ClassificationAgentState.VALIDATION_COUNT to 0,
                ClassificationAgentState.VALIDATION_ERROR to ""
            )
        } else {
            if (validationCount < 2 ) {
                mutableMapOf(
                    ClassificationAgentState.NEXT_AGENT to AgentNode.CLASSIFIER.key,
                    ClassificationAgentState.VALIDATION_COUNT to validationCount + 1,
                    ClassificationAgentState.VALIDATION_ERROR to validationResult.error!!
                )
            } else {
                mutableMapOf(
                    ClassificationAgentState.NEXT_AGENT to ClassificationAgentState.END,
                    ClassificationAgentState.OUTPUT to AgentResult(emptyList()),
                    ClassificationAgentState.VALIDATION_COUNT to 0
                )
            }
        }
    }

    private fun validate(obj: String): ValidationResult {
        try {
            val result = objectMapper.readValue(obj, AgentResult::class.java)
            return ValidationResult(isSuccess = true, result = result)
        } catch (e: Exception) {
            val errorMessage = "Возникла ошибка при парсинге результата: " +
                "${e.javaClass.simpleName}\n${e.message ?: e.localizedMessage}"
            return ValidationResult(isSuccess = false, error = errorMessage)
        }
    }

    data class ValidationResult(val isSuccess: Boolean, val result: AgentResult? = null, val error: String? = null)
}

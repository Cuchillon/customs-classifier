package com.ferick.classifier.service.agents.states

import com.ferick.classifier.model.dto.AgentResult
import org.bsc.langgraph4j.state.AgentState
import org.bsc.langgraph4j.state.Channel
import org.bsc.langgraph4j.state.Channels

class ClassificationAgentState(initData: Map<String, Any>) : AgentState(initData) {

    fun input(): String {
        return this.value<String>(INPUT).orElse("")
    }

    fun context(): String {
        return this.value<String>(CONTEXT).orElse("")
    }

    fun classificationResult(): String {
        return this.value<String>(CLASSIFICATION_RESULT).orElse("")
    }

    fun validationCount(): Int {
        return this.value<Int>(VALIDATION_COUNT).orElse(0)
    }

    fun validationError(): String? {
        return this.value<String>(VALIDATION_ERROR).orElse("")
    }

    fun output(): AgentResult? {
        return this.value<AgentResult>(OUTPUT).orElse(null)
    }

    companion object {
        const val INPUT = "input"
        const val CONTEXT = "context"
        const val NEXT_AGENT = "next_agent"
        const val CLASSIFICATION_RESULT = "classification_result"
        const val VALIDATION_COUNT = "validation_count"
        const val VALIDATION_ERROR = "validation_error"
        const val OUTPUT = "output"

        val SCHEMA: Map<String, Channel<*>> = mapOf(
            INPUT to Channels.base<String>(null, null),
            CONTEXT to Channels.base<String>(null, null),
            CLASSIFICATION_RESULT to Channels.base<String>(null, null),
            NEXT_AGENT to Channels.base<String>(null, null),
            VALIDATION_COUNT to Channels.base<Int>(null, null),
            VALIDATION_ERROR to Channels.base<String>(null, null),
            OUTPUT to Channels.base<AgentResult>(null, null)
        )
    }
}

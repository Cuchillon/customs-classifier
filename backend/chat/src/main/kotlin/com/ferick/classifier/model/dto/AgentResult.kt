package com.ferick.classifier.model.dto

data class AgentResult(
    val answer: List<AgentResultItem>
)

data class AgentResultItem(
    val id: Long,
    val code: String
)

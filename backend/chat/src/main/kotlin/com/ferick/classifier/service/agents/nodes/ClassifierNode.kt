package com.ferick.classifier.service.agents.nodes

import com.ferick.classifier.configuration.properties.ChatProperties
import com.ferick.classifier.service.agents.states.ClassificationAgentState
import org.bsc.langgraph4j.action.NodeAction
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.stereotype.Component

@Component
class ClassifierNode(
    private val chatProperties: ChatProperties,
    private val chatClient: ChatClient
) : NodeAction<ClassificationAgentState> {

    override fun apply(state: ClassificationAgentState): MutableMap<String, Any> {
        val input = state.input()
        val context = state.context()
        val validationCount = state.validationCount()
        val validationError = state.validationError()

        val description = if (!validationError.isNullOrBlank() && validationCount > 0) {
            """
            Предыдущая попытка сгенерировать ответ не прошла валидацию.
            Ошибка: $validationError
            
            Пожалуйста, исправь ответ, строго следуя правилам:
            1. Верни только валидный JSON без пояснений.
            2. Не используй markdown-обёртки.
            3. Убедись, что все поля соответствуют схеме.
            
            Исходный запрос:
            $input
            """.trimIndent()
        } else {
            input
        }

        val prompt = PromptTemplate.builder()
            .template(chatProperties.prompt)
            .variables(
                mapOf(
                    "context" to context,
                    "description" to description
                )
            )
            .build().create()
        val result = chatClient
            .prompt(prompt)
            .call()
            .content()
            ?.trim()
            ?.removeMarkdownCodeFences()
            ?: ""
        return mutableMapOf(
            ClassificationAgentState.NEXT_AGENT to AgentNode.VALIDATOR.key,
            ClassificationAgentState.CLASSIFICATION_RESULT to result
        )
    }

    private fun String.removeMarkdownCodeFences(): String {
        return this.replace(Regex("^```(?:json)?\\s*|\\s*```$"), "").trim()
    }
}

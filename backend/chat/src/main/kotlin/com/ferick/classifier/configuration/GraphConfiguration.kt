package com.ferick.classifier.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.ferick.classifier.configuration.properties.ChatProperties
import com.ferick.classifier.configuration.serializers.AgentResultSerializer
import com.ferick.classifier.model.dto.AgentResult
import com.ferick.classifier.service.agents.nodes.AgentNode
import com.ferick.classifier.service.agents.nodes.ClassifierNode
import com.ferick.classifier.service.agents.nodes.ValidatorNode
import com.ferick.classifier.service.agents.states.ClassificationAgentState
import org.bsc.langgraph4j.CompiledGraph
import org.bsc.langgraph4j.StateGraph
import org.bsc.langgraph4j.action.AsyncEdgeAction.edge_async
import org.bsc.langgraph4j.action.AsyncNodeAction.node_async
import org.bsc.langgraph4j.serializer.std.ObjectStreamStateSerializer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ChatProperties::class)
class GraphConfiguration {

    @Bean
    fun classificationGraph(
        agentResultSerializer: AgentResultSerializer,
        classifierNode: ClassifierNode,
        validatorNode: ValidatorNode
    ): CompiledGraph<ClassificationAgentState> =
        StateGraph(ClassificationAgentState.SCHEMA) { ClassificationAgentState(it) }
            .apply {
                (stateSerializer as ObjectStreamStateSerializer)
                    .mapper().register(AgentResult::class.java, agentResultSerializer)
            }
            .addNode(AgentNode.CLASSIFIER.key, node_async(classifierNode))
            .addNode(AgentNode.VALIDATOR.key, node_async(validatorNode))
            .addEdge(StateGraph.START, AgentNode.CLASSIFIER.key)
            .addEdge(AgentNode.CLASSIFIER.key, AgentNode.VALIDATOR.key)
            .addConditionalEdges(
                AgentNode.VALIDATOR.key,
                edge_async { state -> state.nextAgent() },
                mapOf(
                    AgentNode.CLASSIFIER.key to AgentNode.CLASSIFIER.key,
                    ClassificationAgentState.END to StateGraph.END
                )
            )
            .compile()

    @Bean
    fun agentResultSerializer(objectMapper: ObjectMapper): AgentResultSerializer =
        AgentResultSerializer(objectMapper)
}

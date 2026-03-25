package com.ferick.classifier.configuration

import com.ferick.classifier.configuration.properties.ChatProperties
import com.ferick.classifier.service.agents.nodes.AgentNode
import com.ferick.classifier.service.agents.nodes.ClassifierNode
import com.ferick.classifier.service.agents.nodes.ValidatorNode
import com.ferick.classifier.service.agents.states.ClassificationAgentState
import org.bsc.langgraph4j.CompiledGraph
import org.bsc.langgraph4j.StateGraph
import org.bsc.langgraph4j.action.AsyncEdgeAction.edge_async
import org.bsc.langgraph4j.action.AsyncNodeAction.node_async
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ChatProperties::class)
class GraphConfiguration {

    @Bean
    fun classificationGraph(
        classifierNode: ClassifierNode,
        validatorNode: ValidatorNode
    ): CompiledGraph<ClassificationAgentState> =
        StateGraph(ClassificationAgentState.SCHEMA) { ClassificationAgentState(it) }
            .addNode(AgentNode.CLASSIFIER.key, node_async(classifierNode))
            .addNode(AgentNode.VALIDATOR.key, node_async(validatorNode))
            .addEdge(StateGraph.START, AgentNode.CLASSIFIER.key)
            .addConditionalEdges(
                AgentNode.VALIDATOR.key,
                edge_async { state -> state.nextAgent() },
                mapOf(
                    AgentNode.CLASSIFIER.key to AgentNode.CLASSIFIER.key,
                    ClassificationAgentState.END to StateGraph.END
                )
            )
            .compile()
}

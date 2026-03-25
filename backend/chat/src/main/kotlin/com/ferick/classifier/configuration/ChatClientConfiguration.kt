package com.ferick.classifier.configuration

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatClientConfiguration {

    @Bean
    fun chatClient(builder: ChatClient.Builder): ChatClient =
        builder.defaultAdvisors(SimpleLoggerAdvisor()).build()
}

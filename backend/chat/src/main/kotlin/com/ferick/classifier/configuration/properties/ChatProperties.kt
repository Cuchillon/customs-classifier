package com.ferick.classifier.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("chat")
data class ChatProperties @ConstructorBinding constructor(
    val prompt: String
)

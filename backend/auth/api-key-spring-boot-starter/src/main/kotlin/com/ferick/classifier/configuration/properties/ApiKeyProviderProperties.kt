package com.ferick.classifier.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("api-key-provider")
data class ApiKeyProviderProperties @ConstructorBinding constructor(
    val enabled: Boolean,
    val url: String
)

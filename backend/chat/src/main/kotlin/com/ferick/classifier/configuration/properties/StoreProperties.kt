package com.ferick.classifier.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("store")
data class StoreProperties @ConstructorBinding constructor(
    val url: String
)

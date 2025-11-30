package com.ferick.classifier.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("initial-users")
data class InitialUsersProperties @ConstructorBinding constructor(
    val admin: InitialUserData,
    val services: List<InitialUserData>
)

data class InitialUserData @ConstructorBinding constructor(
    val username: String,
    val password: String,
    val role: String
)

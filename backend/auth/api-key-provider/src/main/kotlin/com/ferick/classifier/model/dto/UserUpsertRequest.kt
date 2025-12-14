package com.ferick.classifier.model.dto

data class UserUpsertRequest(
    val username: String,
    val password: String,
    val roles: Set<Role>
)

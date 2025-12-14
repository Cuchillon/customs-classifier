package com.ferick.classifier.repository

import com.ferick.classifier.model.entities.ApiKey
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ApiKeyRepository : JpaRepository<ApiKey, Long> {
    fun findByKeyId(keyId: UUID): ApiKey?
}

package com.ferick.classifier.model.entities

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "api_keys")
data class ApiKey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val keyId: UUID,
    val keyHash: String,
    val username: String,
    val createdAt: Instant = Instant.now(),
    val expiresAt: Instant? = null,
    var active: Boolean = true,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "api_key_scopes", joinColumns = [JoinColumn(name = "api_key_id")])
    @Column(name = "scope")
    val scopes: MutableSet<String> = mutableSetOf()
)

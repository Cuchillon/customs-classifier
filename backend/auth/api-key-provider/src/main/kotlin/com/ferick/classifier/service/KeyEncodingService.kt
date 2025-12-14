package com.ferick.classifier.service

import java.util.UUID

interface KeyEncodingService {
    fun generateRawKey(keyId: UUID): String
    fun hashKey(rawKey: String): String
    fun matches(rawKey: String, hash: String): Boolean
}

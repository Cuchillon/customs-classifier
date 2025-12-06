package com.ferick.classifier.service.impl

import com.ferick.classifier.service.KeyEncodingService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import java.util.UUID

@Service
class KeyEncodingServiceImpl(
    private val passwordEncoder: PasswordEncoder
) : KeyEncodingService {

    private val random = SecureRandom()

    override fun generateRawKey(keyId: UUID): String {
        val secretBytes = ByteArray(32)
        random.nextBytes(secretBytes)
        val secretB64 = Base64.getUrlEncoder().withoutPadding().encodeToString(secretBytes)
        return "sk_${keyId}_$secretB64"
    }

    override fun hashKey(rawKey: String): String {
        val preHash = sha256(rawKey)
        return passwordEncoder.encode(preHash)
    }

    override fun matches(rawKey: String, hash: String): Boolean {
        val preHash = sha256(rawKey)
        return passwordEncoder.matches(preHash, hash)
    }

    private fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
            .digest(input.toByteArray(StandardCharsets.UTF_8))
        return Base64.getEncoder().encodeToString(digest)
    }
}
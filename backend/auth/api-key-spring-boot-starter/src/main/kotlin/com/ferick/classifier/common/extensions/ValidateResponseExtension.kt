package com.ferick.classifier.common.extensions

import com.ferick.classifier.model.dto.ValidateResponse
import java.time.Instant

fun ValidateResponse.isPermissionEnough(appScopes: List<String>): Boolean {
    val scopes = this.data?.scopes
    return when {
        scopes.isNullOrEmpty() && appScopes.isNotEmpty() -> false
        scopes.isNullOrEmpty() && appScopes.isEmpty() -> true
        else -> scopes!!.containsAll(appScopes)
    }
}

fun ValidateResponse.isExpired(): Boolean {
    val expiresAt = this.data?.expiresAt ?: return true
    return expiresAt.isBefore(Instant.now())
}

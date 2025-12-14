package com.ferick.classifier.common.extensions

import com.ferick.classifier.model.dto.ValidateResponse

fun ValidateResponse.isPermissionEnough(appScopes: List<String>): Boolean {
    val scopes = this.data?.scopes
    return when {
        scopes.isNullOrEmpty() && appScopes.isNotEmpty() -> false
        scopes.isNullOrEmpty() && appScopes.isEmpty() -> true
        else -> scopes!!.containsAll(appScopes)
    }
}

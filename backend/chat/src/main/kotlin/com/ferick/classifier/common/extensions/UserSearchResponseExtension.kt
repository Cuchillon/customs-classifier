package com.ferick.classifier.common.extensions

import com.ferick.classifier.model.dto.UserSearchResponse

fun UserSearchResponse.toContext(): String =
    this.items
        .sortedByDescending { it.score }
        .joinToString(separator = "\n") { "Код товара: ${it.code}, описание: ${it.text}" }

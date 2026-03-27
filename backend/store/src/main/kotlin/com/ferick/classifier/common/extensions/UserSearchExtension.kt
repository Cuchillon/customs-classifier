package com.ferick.classifier.common.extensions

import com.ferick.classifier.model.dto.UserSearchBatchRequest
import com.ferick.classifier.model.dto.UserSearchRequest

fun UserSearchBatchRequest.toSearchRequests(): List<UserSearchRequest> =
    this.queries.map { UserSearchRequest(it, topK, similarityThreshold, meta) }

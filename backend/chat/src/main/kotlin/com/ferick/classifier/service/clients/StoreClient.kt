package com.ferick.classifier.service.clients

import com.ferick.classifier.model.dto.UserSearchBatchRequest
import com.ferick.classifier.model.dto.UserSearchResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.WebClientResponseException

@Component
class StoreClient(
    private val restClient: RestClient
) {

    fun loadContext(request: UserSearchBatchRequest): UserSearchResponse =
        try {
            restClient.post()
                .uri("/search/batch")
                .body(request)
                .retrieve()
                .body(UserSearchResponse::class.java) ?: throw RuntimeException("Response is null")
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is WebClientResponseException -> "Status ${e.statusCode}, error ${e.message}"
                else -> e.message ?: e.localizedMessage
            }
            throw RuntimeException(errorMessage)
        }
}

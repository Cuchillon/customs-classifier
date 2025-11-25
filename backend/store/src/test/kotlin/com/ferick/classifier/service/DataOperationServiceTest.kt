package com.ferick.classifier.service

import com.ferick.classifier.model.dto.UserSearchRequest
import com.ferick.classifier.service.impl.DataOperationServiceImpl
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DataOperationServiceTest {

    private val documentReaderService: DocumentReaderService = mockk()
    private val vectorStoreService: VectorStoreService = mockk()

    private val dataOperationService: DataOperationService = DataOperationServiceImpl(
        documentReaderService, vectorStoreService
    )

    @Test
    fun `search data returns response with empty items`() {
        val request = UserSearchRequest(
            query = "User query text",
            topK = 4,
            similarityThreshold = 0.90
        )
        every { vectorStoreService.searchDocuments(any()) } returns emptyList()

        val result = dataOperationService.searchData(request)
        assertThat(result.items).isEmpty()
    }
}

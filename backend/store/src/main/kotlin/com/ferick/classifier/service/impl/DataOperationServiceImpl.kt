package com.ferick.classifier.service.impl

import com.ferick.classifier.common.extensions.toSearchRequests
import com.ferick.classifier.common.extensions.toSearchResponseItem
import com.ferick.classifier.model.dto.StoreRequest
import com.ferick.classifier.model.dto.UserSearchBatchRequest
import com.ferick.classifier.model.dto.UserSearchRequest
import com.ferick.classifier.model.dto.UserSearchResponse
import com.ferick.classifier.service.DataOperationService
import com.ferick.classifier.service.DocumentReaderService
import com.ferick.classifier.service.VectorStoreService
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service

@Service
class DataOperationServiceImpl(
    private val documentReaderService: DocumentReaderService,
    private val vectorStoreService: VectorStoreService
) : DataOperationService {

    override fun storeData(request: StoreRequest) {
        val resource = ByteArrayResource(request.data)
        val documents = documentReaderService
            .getDocuments(resource, request.meta, request.fileName.validate())
        vectorStoreService.storeDocuments(documents)
    }

    override fun searchData(request: UserSearchRequest): UserSearchResponse =
        vectorStoreService.searchDocuments(request)
            .map { it.toSearchResponseItem() }
            .let { UserSearchResponse(it) }

    override fun batchSearchData(request: UserSearchBatchRequest): UserSearchResponse =
        request.toSearchRequests().flatMap { searchRequest ->
            vectorStoreService.searchDocuments(searchRequest).map { it.toSearchResponseItem() }
        }.let { UserSearchResponse(it) }

    companion object {
        private fun String?.validate(): String {
            if (this.isNullOrBlank()) {
                throw IllegalArgumentException("File name must be present")
            }
            return this
        }
    }
}
